package se.artcomputer.chat.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BotService {
    private static final Logger LOG = LoggerFactory.getLogger(BotService.class);

    private static final Map<String, Bot> bots = new HashMap<>();

    public void spawnBort(String botName) {
        LOG.info("Spawn bot {}", botName);
        bots.put(botName, new Bot(botName).start());
    }

    public List<BotDto> getBots() {
        return bots.values().stream().map(this::toDto).collect(Collectors.toList());
    }

    private BotDto toDto(Bot bot) {
        return new BotDto(bot.botName);
    }
}

