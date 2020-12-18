package se.artcomputer.chat.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static se.artcomputer.chat.generator.BotState.RUNNING;

public class Bot extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);
    public final String botName;
    private BotState state = BotState.INITIAL;
    private final BotScript botScript;

    public Bot(String botName) {
        this.botName = botName;
        botScript = new BotScript();
    }

    public Bot startMeUp() {
        LOG.info("Bot {}  starting", botName);
        this.start();
        return this;
    }

    public Bot stopMeNow() {
        LOG.info("Bot {} stopping.", botName);
        state = BotState.STOPPED;
        return this;
    }

    @Override
    public void run() {
        LOG.info("New thread");
        state = RUNNING;
        try {
            while (state == RUNNING) {
                botScript.doNext();
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            LOG.info("Thread interrupted.");
        }
        LOG.info("End of run.");
    }

}

