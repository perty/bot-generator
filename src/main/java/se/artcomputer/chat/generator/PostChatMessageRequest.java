package se.artcomputer.chat.generator;

public class PostChatMessageRequest {

    private final String message;

    public PostChatMessageRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

