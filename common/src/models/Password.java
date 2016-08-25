package models;

public class Password {

	private String newPassword;
	private String confirmPassword;
	
	public Password(String pass) {
		newPassword = pass;
		confirmPassword = pass;
	}

	public Password() {
	}
	
	public void setNewPassword(String pass) {
		newPassword = pass;
	}

	public void setConfPassword(String pass) {
		confirmPassword = pass;
	}

	public String getConfPassword() {
		return confirmPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
}

