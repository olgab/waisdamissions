package nl.waisda.forms;

import nl.waisda.domain.User;

public class RequestResetForm {

	private String email;

	private User user;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
