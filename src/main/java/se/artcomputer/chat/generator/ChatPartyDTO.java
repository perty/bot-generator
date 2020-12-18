package se.artcomputer.chat.generator;

public class ChatPartyDTO {
    private String name;
    private long lastRead;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastRead() {
        return lastRead;
    }

    public void setLastRead(long lastRead) {
        this.lastRead = lastRead;
    }
}
