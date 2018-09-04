package com.randominfo.bot.processors;

import com.randominfo.bot.services.BotService;
import com.randominfo.bot.services.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class IncomeMessageProcessor {

    @Autowired
    private LoggerService log;

    @Autowired
    private BotService botService;

    public void process(Message message) {
        log.debug("Message: {}", message);
        if (message.getText().toLowerCase().startsWith("привет")){
            Long chatId = message.getChatId();
            SendMessage sendMessage = new SendMessage()
                    .setText("Hello!")
                    .setChatId(chatId);

            try {
                botService.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
