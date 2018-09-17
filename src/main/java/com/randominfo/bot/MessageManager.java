package com.randominfo.bot;

import com.randominfo.bot.services.BotService;
import com.randominfo.bot.services.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MessageManager {
    @Autowired
    private BotService botService;

    @Autowired
    private LoggerService log;


    public void execute(SendMessage sendMessage) {
        try {
            botService.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Can not send message: ", e);
        }
    }
}
