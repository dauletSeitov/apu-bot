package pipisya.bot.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import pipisya.bot.service.DickService;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotInput extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private DickService dickService;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        log.info("update: {}", update);
        try {
            if (update.hasMessage() && !StringUtils.isBlank(update.getMessage().getText())) {
                String message = update.getMessage().getText();
                String type = update.getMessage().getChat().getType();
                if ("group".equals(type) || "supergroup".equals(type)) {//group
                    if (message.startsWith("/top_dick")) {
                        dickService.topDick(update.getMessage());
                    } else if (message.startsWith("/dick")) {
                        dickService.dickPlay(update.getMessage());
                    }
                } else {
                    if (message.startsWith("/get_all")) {//private
                        dickService.getAll(update.getMessage());
                    } else if (message.startsWith("/save_one")) {
                        dickService.save(update.getMessage());
                    } else if (message.startsWith("/delete_one")) {
                        dickService.delete(update.getMessage());
                    } else if (message.startsWith("/set_check_already_played")) {
                        dickService.setCheckAlreadyPlayed();
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}