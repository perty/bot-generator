package se.artcomputer.chat.generator;

public class LoginRequest {
    private EmailAddress email;
    private String password;

    public EmailAddress getEmail() {
        return email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
