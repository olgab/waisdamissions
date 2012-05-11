package nl.waisda.validators;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.regex.Pattern;

import nl.waisda.domain.ResetPassword;
import nl.waisda.domain.User;
import nl.waisda.forms.AuthForm;
import nl.waisda.forms.ProfileForm;
import nl.waisda.forms.RegisterForm;
import nl.waisda.forms.RequestResetForm;
import nl.waisda.forms.ResetPasswordForm;
import nl.waisda.repositories.ResetPasswordRepository;
import nl.waisda.repositories.UserRepository;

import org.apache.commons.lang.StringUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {

	public static Pattern emailPattern = Pattern.compile("[^@]+@[^@]+");

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ResetPasswordRepository rpwRepo;

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterForm.class.equals(clazz)
				|| ProfileForm.class.equals(clazz)
				|| RequestResetForm.class.equals(clazz)
				|| ResetPasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		if (obj instanceof RegisterForm) {
			validateRegisterForm((RegisterForm) obj, e);
		} else if (obj instanceof ProfileForm) {
			validateProfileForm((ProfileForm) obj, e);
		} else if (obj instanceof RequestResetForm) {
			validateRequestResetForm((RequestResetForm) obj, e);
		} else if (obj instanceof ResetPasswordForm) {
			validateResetPasswordForm((ResetPasswordForm) obj, e);
		}
	}

	public void validateRegisterForm(RegisterForm form, Errors e) {
		validateSetAuthForm(form.getAuth(), e);

		form.setEmail(removeWhitespace(form.getEmail()));

		if (!acceptEmail(form.getEmail())) {
			e.rejectValue("email", "Register.email.invalid");
		}

		if (!form.isAgreeTos()) {
			e.rejectValue("agreeTos", "Register.agreeTos.invalid");
		}

		User teacherByEmail = userRepo.getUserByEmail(form.getEmail());
		if (teacherByEmail != null) {
			e.rejectValue("email", "Register.email.duplicate");
		}

		validateDuplicateName(form.getAuth(), null, e);
		validatePasswordReset(form.getAuth(), e);
	}

	private void validateProfileForm(ProfileForm form, Errors e) {
		validateSetAuthForm(form.getAuth(), e);

		if (form.getDateOfBirth().length() > 0) {
			try {
				form.setFormattedDateOfBirth(User.DATE_FORMAT.parse(form
						.getDateOfBirth()));
			} catch (ParseException x) {
				e.rejectValue("dateOfBirth", "Profile.dateOfBirth.invalid");
			}
		} else {
			form.setFormattedDateOfBirth(null);
		}

		validateDuplicateName(form.getAuth(), form.getId(), e);
		if (form.getAuth().filledPassword()) {
			validatePasswordReset(form.getAuth(), e);
			if (!validateCurrentPassword(form, e)) {
				e.rejectValue("currentPassword", "Profile.password.invalid");
			}
		}

		if (!isValidUsername(form.getUsernameFacebook(), "facebook.com")) {
			e.rejectValue("usernameFacebook",
					"Profile.socialmediaUsername.invalid");
		}
		if (!isValidUsername(form.getUsernameHyves(), "hyves.nl")) {
			e.rejectValue("usernameHyves",
					"Profile.socialmediaUsername.invalid");
		}
		if (!isValidUsername(form.getUsernameTwitter(), "twitter.com")) {
			e.rejectValue("usernameTwitter",
					"Profile.socialmediaUsername.invalid");
		}
	}

	private boolean isValidUsername(String usernameOrUrl, String forbiddenHost) {
		if (StringUtils.isBlank(usernameOrUrl))
			return true;

		usernameOrUrl = usernameOrUrl.trim();

		try {
			URL url = new URL(usernameOrUrl.contains("://") ? usernameOrUrl
					: "http://" + usernameOrUrl);
			return !url.getHost().equals(forbiddenHost);
		} catch (MalformedURLException e) {
			return true;
		}
	}

	private void validateDuplicateName(AuthForm form, Integer currentUserId,
			Errors e) {
		User existing = userRepo.getUserByName(form.getName());

		if (existing != null
				&& (currentUserId == null || existing.getId() != currentUserId)) {
			e.rejectValue("auth.name", "SetAuth.name.duplicate");
		}
	}

	private void validatePasswordReset(AuthForm form, Errors e) {
		if (form.getPassword().length() < 6) {
			e.rejectValue("auth.password", "SetAuth.password.tooShort");
		}

		if (!form.getPassword().equals(form.getRepeatPassword())) {
			e.rejectValue("auth.repeatPassword",
					"SetAuth.repeatPassword.invalid");
		}
	}

	private boolean validateCurrentPassword(ProfileForm form, Errors e) {
		User user = this.userRepo.getById(form.getId());
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		if (passwordEncryptor.checkPassword(form.getCurrentPassword(),
				user.getSaltedPassword())) {
			return true;
		}
		return false;
	}

	private void validateSetAuthForm(AuthForm form, Errors e) {
		e.pushNestedPath("auth");

		form.setName(normalizeWhitespace(form.getName()));

		if (form.getName().length() == 0) {
			e.rejectValue("name", "SetAuth.name.blank");
		} else if (form.getName().length() > 24) {
			e.rejectValue("name", "SetAuth.name.tooLong");
		} else if (form.getName().length() < 2) {
			e.rejectValue("name", "SetAuth.name.tooShort");
		}

		e.popNestedPath();
	}

	private void validateRequestResetForm(RequestResetForm form, Errors e) {
		if (form.getEmail() == null || form.getEmail().equals("")) {
			e.rejectValue("email", "RequestReset.email.blank");
		} else {
			User user = userRepo.getUserByEmail(form.getEmail());
			if (user == null) {
				e.rejectValue("email", "RequestReset.email.nonexistent");
			} else {
				form.setUser(user);
			}
		}
	}

	private void validateResetPasswordForm(ResetPasswordForm form, Errors e) {
		ResetPassword reset = rpwRepo.getById(form.getId());
		if (reset == null) {
			e.rejectValue("id", "404");
		} else {
			form.setResetPassword(reset);
			if (!reset.isPending() || reset.hasExpired()) {
				e.rejectValue("id", "ResetPassword.expired");
			} else if (!reset.isPlainTextKeyCorrect(form.getPlainTextKey())) {
				e.rejectValue("plainTextKey", "ResetPassword.key.incorrect");
			} else {
				validatePasswordReset(form.getAuth(), e);
			}
		}
	}

	public static String normalizeWhitespace(String s) {
		return s.trim().replaceAll("\\s+", " ");
	}

	public static String removeWhitespace(String s) {
		return s.replaceAll("\\s+", "");
	}

	public static boolean acceptEmail(String email) {
		return emailPattern.matcher(email).matches();
	}

}
