package nl.waisda.forms;

import nl.waisda.domain.ResetPassword;

public class ResetPasswordForm {

	private int id;
	private String plainTextKey;
	private ResetPassword resetPassword;
	private AuthForm auth = new AuthForm();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlainTextKey() {
		return plainTextKey;
	}

	public void setPlainTextKey(String plainTextKey) {
		this.plainTextKey = plainTextKey;
	}

	public ResetPassword getResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(ResetPassword resetPassword) {
		this.resetPassword = resetPassword;
	}

	public AuthForm getAuth() {
		return auth;
	}

	public void setAuth(AuthForm auth) {
		this.auth = auth;
	}

}
