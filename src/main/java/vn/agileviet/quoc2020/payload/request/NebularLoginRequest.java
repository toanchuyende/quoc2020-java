package vn.agileviet.quoc2020.payload.request;

import javax.validation.constraints.NotBlank;

public class NebularLoginRequest {
	@NotBlank
	private String email;

	@NotBlank
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	private boolean rememberMe;

}
