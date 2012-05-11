package nl.waisda.forms;

import nl.waisda.domain.User;

public class RegisterForm implements ApplyForm<User> {

	private String email;
	private AuthForm auth = new AuthForm();
	private boolean agreeTos;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AuthForm getAuth() {
		return auth;
	}

	public void setAuth(AuthForm auth) {
		this.auth = auth;
	}

	public boolean isAgreeTos() {
		return agreeTos;
	}

	public void setAgreeTos(boolean agreeTos) {
		this.agreeTos = agreeTos;
	}

	@Override
	public void applyTo(User user) {
		user.setEmail(email);
		auth.applyTo(user);
		user.setCreationDate();
	}

}
