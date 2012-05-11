package nl.waisda.forms;

import nl.waisda.domain.User;

import org.apache.commons.lang.StringUtils;


public class AuthForm implements ApplyForm<User>, FillForm<User> {

	private User existingUser;

	private String name;
	private String password;
	private String repeatPassword;

	public User getExistingUser() {
		return existingUser;
	}

	public void setExistingUser(User existingUser) {
		this.existingUser = existingUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	public boolean filledPassword() {
		return !StringUtils.isBlank(getPassword()) 
			|| !StringUtils.isBlank(getRepeatPassword());
	}

	@Override
	public void applyTo(User user) {
		user.setName(name);
		if (password.length() > 0) {
			user.setPlainTextPassword(password);
		}
	}

	public void fillFrom(User user) {
		name = user.getName();
		existingUser = user;
	}

}
