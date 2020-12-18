package se.artcomputer.chat.generator;

public class EmailAddress extends StringWithMaxLength {

    private static final int EMAIL_ADDRESS_MAX_LENGTH = 256;

    public EmailAddress(String emailAddress) {
        super(emailAddress);
    }

    public EmailAddress() {
        // Jackson requirement
    }

    @Override
    protected int getMaxLength() {
        return EMAIL_ADDRESS_MAX_LENGTH;
    }

    public void setEmailAddress(String emailAddress) {
        super.setValue(emailAddress);
    }

    public String getEmailAddress() {
        return super.getValue();
    }
}

