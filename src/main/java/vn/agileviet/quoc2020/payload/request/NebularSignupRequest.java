package vn.agileviet.quoc2020.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NebularSignupRequest {
    @NotBlank
    @Size(min = 4, max = 500)
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isTerms() {
        return terms;
    }

    public void setTerms(boolean terms) {
        this.terms = terms;
    }

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;


    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


    @NotBlank
    @Size(min = 6, max = 40)
    private String confirmPassword;
    private boolean terms;


}
