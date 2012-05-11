package nl.waisda.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import nl.waisda.domain.User;
import nl.waisda.exceptions.Forbidden;
import nl.waisda.exceptions.NotFoundException;
import nl.waisda.forms.ProfileForm;
import nl.waisda.model.Profile;
import nl.waisda.repositories.UserRepository;
import nl.waisda.services.UserService;
import nl.waisda.services.UserSessionService;
import nl.waisda.validators.UberValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@Autowired
	private UberValidator validator;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserSessionService userSessionService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/profiel/{id}", method = RequestMethod.GET)
	public String profile(@PathVariable int id, ModelMap model,
			HttpSession session) throws NotFoundException {

		Profile profile = userService.getProfile(id);

		if (profile == null) {
			throw new NotFoundException("User not found: " + id);
		}

		model.addAttribute("profile", profile);

		User viewingUser = userSessionService.getCurrentUser(session);
		if (viewingUser != null
				&& viewingUser.getId() == profile.getUser().getId()) {
			ProfileForm form = new ProfileForm();
			form.fillFrom(profile.getUser());
			model.addAttribute("form", form);
		}

		return "profile";
	}

	@RequestMapping(value = "/profiel/{id}", method = RequestMethod.POST)
	public ModelAndView editProfile(@PathVariable int id, @Valid @ModelAttribute("form") ProfileForm form, 
			Errors errors, HttpSession session)  throws NotFoundException, Forbidden {
		
		User currentUser = userSessionService.getCurrentUser(session);
		
		if (currentUser.getId() != form.getId()) {
			throw new Forbidden();
		}
		
		if (errors.hasErrors()) {
			ModelMap model = new ModelMap();
			String view = profile(form.getId(), model, session);
			model.addAttribute("form", form);
			model.addAttribute("errors", errors.hasErrors());
			model.addAttribute("showPassword", form.getAuth().filledPassword());
			return new ModelAndView(view, model);
		}
		else {
			form.applyTo(currentUser);
			userRepo.store(currentUser);
			return new ModelAndView("redirect:/profiel/" + form.getId());
		}
	}

}
