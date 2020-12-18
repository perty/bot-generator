package se.artcomputer.chat.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class BotScript {
    private static final Logger LOG = LoggerFactory.getLogger(BotScript.class);

    private final WebClient webClient;
    private ScriptState state = ScriptState.INITIAL;
    private String sessionId;

    enum ScriptState {
        INITIAL, LOGGED_IN
    }

    public BotScript() {
        webClient = WebClient
                .builder()
                .baseUrl("http://localhost:5000/api/v2")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
                .build();
    }

    public void doNext() {
        LOG.info("Do next");
        if(state == ScriptState.INITIAL) {
            login();
        }
    }

    private void login() {
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
            this.state = ScriptState.LOGGED_IN;
        }
    }
}

