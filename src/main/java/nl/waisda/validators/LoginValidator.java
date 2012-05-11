package nl.waisda.validators;

import nl.waisda.domain.User;
import nl.waisda.forms.LoginForm;
import nl.waisda.repositories.UserRepository;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class LoginValidator implements Validator {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return LoginForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		LoginForm form = (LoginForm) obj;

		User user = userRepo.getUserByEmail(form.getEmailaddress());
		if (user == null) {
			e.reject("LoginForm.invalidCredentials");
		}
		else {
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			if (passwordEncryptor.checkPassword(form.getPassword(), user.getSaltedPassword())) {
				form.setUser(user);
			} else {
				e.reject("LoginForm.invalidCredentials");
			}
		}
	}

}
