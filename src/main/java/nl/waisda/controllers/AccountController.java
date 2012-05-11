package nl.waisda.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import nl.waisda.domain.ResetPassword;
import nl.waisda.domain.User;
import nl.waisda.exceptions.NotFoundException;
import nl.waisda.forms.LoginForm;
import nl.waisda.forms.RegisterForm;
import nl.waisda.forms.RequestResetForm;
import nl.waisda.forms.ResetPasswordForm;
import nl.waisda.repositories.ResetPasswordRepository;
import nl.waisda.repositories.UserRepository;
import nl.waisda.services.MailService;
import nl.waisda.services.UserService;
import nl.waisda.services.UserSessionService;
import nl.waisda.validators.UberValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ResetPasswordRepository rpwRepo;

	@Autowired
	private UberValidator validator;

	@Autowired
	private MailService mailService;

	private static Logger log = Logger.getLogger(AccountController.class);

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping("/registreren")
	public String register(ModelMap model, HttpSession session) {
		User user = userSessionService.getCurrentUser(session);
		if (user == null || user.isAnonymous()) {
			model.addAttribute("form", new RegisterForm());
			return "register";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/registreren", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("form") RegisterForm form,
			Errors errors, HttpSession session) {
		if (errors.hasErrors()) {
			return "register";
		} else {
			User user = new User();
			form.applyTo(user);
			userRepo.store(user);
			userSessionService.login(session, user);
			log.info(String.format("Registered %s!", user.getShortDescription()));
			return "redirect:/";
		}
	}

	@RequestMapping("/inloggen")
	public String login(ModelMap model, HttpSession session) {
		User currentUser = userSessionService.getCurrentUser(session);
		if (currentUser == null || currentUser.isAnonymous()) {
			LoginForm loginForm = new LoginForm();
			model.addAttribute("loginForm", loginForm);
			return "login";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/inloggen", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute LoginForm loginForm,
			Errors errors, HttpSession session) {
		User newUser = loginForm.getUser();

		if (newUser != null) {
			userSessionService.login(session, newUser);
			log.info(String.format("Logged in %s!",
					newUser.getShortDescription()));
			return "redirect:/";
		} else {
			return "login";
		}
	}

	@RequestMapping("/uitloggen")
	public String logout(HttpSession session) {
		userSessionService.logout(session);
		return "redirect:/";
	}

	@RequestMapping("/check-cookies")
	public String cookies(HttpSession session, ModelMap model, String targetUrl) {
		if (targetUrl == null || targetUrl == "") {
			targetUrl = "/";
		} else {
			// Check that the URL is local.
			if (targetUrl.contains("://")) {
				throw new IllegalArgumentException("Invalid target url: "
						+ targetUrl);
			}
		}
		if (session.isNew()) {
			model.put("targetUrl", targetUrl);
			return "cookies";
		} else {
			return "redirect:" + targetUrl;
		}
	}

	@RequestMapping(value = "/wachtwoord-vergeten", method = RequestMethod.GET)
	public String wachtwoordVergeten(HttpSession session, ModelMap model) {
		RequestResetForm emailForm = new RequestResetForm();
		model.addAttribute("form", emailForm);
		return "wachtwoord-vergeten";
	}

	@RequestMapping(value = "/wachtwoord-vergeten", method = RequestMethod.POST)
	public String wachtwoordVergeten(
			@Valid @ModelAttribute("form") RequestResetForm form,
			Errors errors, HttpSession session, ModelMap model) {
		if (errors.hasErrors()) {
			return "wachtwoord-vergeten";
		}

		User user = form.getUser();
		ResetPassword reset = userService.requestPasswordReset(user);
		log.info(reset.getPlainTextKey());
		mailService.sendPasswordResetMail(reset);

		return "wachtwoord-vergeten";
	}

	@RequestMapping(value = "/wachtwoord-veranderen", method = RequestMethod.GET)
	public String wachtwoordVeranderen(HttpSession session, ModelMap model,
			int id, String key) throws NotFoundException {

		ResetPassword reset = rpwRepo.getById(id);
		if (reset == null) {
			throw new NotFoundException();
		} else {
			if (!reset.isPending() || reset.hasExpired()) {
				model.addAttribute("errorMessage",
						"Deze link is verlopen of al een keer gebruikt.");
				return "wachtwoord-veranderen";
			} else if (!reset.isPlainTextKeyCorrect(key)) {
				model.addAttribute("errorMessage",
						"De code klopt niet. Heeft u de link goed gekopieerd?");
				return "wachtwoord-veranderen";
			} else {
				ResetPasswordForm form = new ResetPasswordForm();
				form.setId(id);
				form.setPlainTextKey(key);
				model.addAttribute("form", form);
				return "wachtwoord-veranderen";
			}
		}
	}

	@RequestMapping(value = "/wachtwoord-veranderen", method = RequestMethod.POST)
	public String wachtwoordVeranderen(
			@Valid @ModelAttribute("form") ResetPasswordForm form,
			Errors errors, HttpSession session, ModelMap model)
			throws NotFoundException {

		if (form.getResetPassword() == null) {
			throw new NotFoundException();
		} else if (errors.hasErrors()) {
			return "wachtwoord-veranderen";
		} else {
			log.info(String.format("Resetting password for user %d", form
					.getResetPassword().getUser().getId()));
			ResetPassword reset = form.getResetPassword();

			User user = reset.getUser();
			user.setPlainTextPassword(form.getAuth().getPassword());
			userRepo.store(user);

			reset.setResetDate();
			rpwRepo.store(reset);

			model.addAttribute("success", true);
			return "wachtwoord-veranderen";
		}
	}

}
