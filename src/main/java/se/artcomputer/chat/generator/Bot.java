package se.artcomputer.chat.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bot {
    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);
    public final String botName;

    public Bot(String botName) {
        this.botName = botName;
    }

    public Bot start() {
        LOG.info("Bot {}  starting", botName);

        return this;
    }
}

