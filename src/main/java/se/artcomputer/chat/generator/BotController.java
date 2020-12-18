package se.artcomputer.chat.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/bots")
public class BotController {
    private static final Logger LOG = LoggerFactory.getLogger(BotController.class);

    private final BotService botService;

    public BotController(BotService botService) {
        this.botService = botService;
    }

    @PostMapping
    public void spawnBot(@RequestBody BotDto bot) {
        LOG.info("Spawn bot {}", bot.botName);

        botService.spawnBort(bot.botName);
    }

    @GetMapping
    public List<BotDto> getBots() {
        LOG.info("Get bots");
        return botService.getBots();
    }
}

