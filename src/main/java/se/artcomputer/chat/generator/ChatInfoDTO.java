package se.artcomputer.chat.generator;

import java.util.List;

public class ChatInfoDTO {
    private String creatorName;
    private String creatorSexYearOrientation;
    private String chatId;
    private String profilePhotoUrl;
    private List<ChatPartyDTO> invited;
    private Long lastUpdate;
    private long newMessages;
    private String firstMessage;
    private boolean theyAreIgnoring;
    private boolean iAmIgnoring;

    private ChatInfoDTO() {
        // Use builder
    }

    public String getCreatorName() {
        return creatorName;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public long getNewMessages() {
        return newMessages;
    }

    public String getChatId() {
        return chatId;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public List<ChatPartyDTO> getInvited() {
        return invited;
    }

    public String getCreatorSexYearOrientation() {
        return creatorSexYearOrientation;
    }

    public String getFirstMessage() {
        return firstMessage;
    }

    public boolean isTheyAreIgnoring() {
        return theyAreIgnoring;
    }

    public boolean isiAmIgnoring() {
        return iAmIgnoring;
    }

}
