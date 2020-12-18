package se.artcomputer.chat.generator;

public class LoginReply {
    private String sessionId;
    private Result result;
    private String userName;
    private String memberId;

    public enum Result {
        OK,
        FALSE_CREDENTIALS
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Result getResult() {
        return result;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "LoginReply{" +
                "sessionId='" + sessionId + '\'' +
                ", result=" + result +
                ", userName='" + userName + '\'' +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
