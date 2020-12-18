package se.artcomputer.chat.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class BotScript {
    private static final Logger LOG = LoggerFactory.getLogger(BotScript.class);
    private static final String BEARER = "Bearer ";

    private final WebClient webClient;
    private String sessionId;
    private Msg nextMsg = Msg.INITIAL;
    private List<ChatInfoDTO> chats;
    private String myUserName;
    private ChatInfoDTO selectedChat;

    enum Msg {
        INITIAL,
        LOGGED_IN,
        MESSAGE_POSTED, CHAT_SELECTED, FETCHED_CHATS
    }

    public BotScript() {
        webClient = WebClient
                .builder()
                .baseUrl("http://localhost:5000/api/v2")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void doNext() {
        LOG.info("Do next {}", nextMsg);
        nextMsg =  switch (nextMsg) {
            case INITIAL -> login();
            case LOGGED_IN -> fetchChats();
            case FETCHED_CHATS -> selectChat();
            case CHAT_SELECTED -> postMessage();
            case MESSAGE_POSTED -> fetchChats();
        };
    }

    private Msg postMessage() {
        LOG.info("Post message in {} ", selectedChat.getCreatorName());
        webClient
                .post()
                .uri("/chats/" + selectedChat.getChatId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + this.sessionId)
                .bodyValue(new PostChatMessageRequest("lsome chat message"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return Msg.MESSAGE_POSTED;
    }

    private Msg selectChat() {
        LOG.info("Select Chat");
        for (ChatInfoDTO chat : chats) {
            ChatMessageDTO lastMessage = getLastMessage(chat);
            if (!lastMessage.getSenderName().equals(myUserName)) {
                this.selectedChat = chat;
                return Msg.CHAT_SELECTED;
            }
        }
        return Msg.FETCHED_CHATS;
    }

    private ChatMessageDTO getLastMessage(ChatInfoDTO chat) {
        ParameterizedTypeReference<List<ChatMessageDTO>> typeRef = new ParameterizedTypeReference<>() {
        };
        List<ChatMessageDTO> list = webClient
                .get()
                .uri("/chats/" + chat.getChatId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + this.sessionId)
                .retrieve()
                .bodyToMono(typeRef)
                .block();
        assert list != null;
        return list
                .stream()
                .sorted(this::sortBySendDateDesc)
                .collect(Collectors.toList())
                .get(0);
    }

    private int sortBySendDateDesc(ChatMessageDTO chatMessageDTO, ChatMessageDTO chatMessageDTO1) {
        return (int) (chatMessageDTO1.getSendTime() - chatMessageDTO.getSendTime());
    }

    private Msg fetchChats() {
        ParameterizedTypeReference<List<ChatInfoDTO>> typeRef = new ParameterizedTypeReference<>() {
        };
        chats = webClient
                .get()
                .uri("/chats")
                .header(HttpHeaders.AUTHORIZATION, BEARER + this.sessionId)
                .retrieve()
                .bodyToMono(typeRef)
                .block();
        assert chats != null;
        LOG.info("Fetched {} chats", chats.size());
        return Msg.FETCHED_CHATS;
    }

    private Msg login() {
        LOG.info("Log in");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(new EmailAddress("bot@mailinator.com"));
        loginRequest.setPassword("secret");
        LoginReply loginReply = webClient
                .post()
                .uri("/members/login")
                .bodyValue(loginRequest)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(LoginReply.class)
                .block();
        LOG.info("Login reply: {}", loginReply);
        assert loginReply != null;
        if (loginReply.getResult() == LoginReply.Result.OK) {
            this.sessionId = loginReply.getSessionId();
            this.myUserName = loginReply.getUserName();
            return Msg.LOGGED_IN;
        }
        return Msg.INITIAL;
    }
}

