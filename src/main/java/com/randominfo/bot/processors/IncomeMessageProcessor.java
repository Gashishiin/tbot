package com.randominfo.bot.processors;

import com.randominfo.bot.MessageManager;
import com.randominfo.bot.domain.Commands;
import com.randominfo.bot.services.BotService;
import com.randominfo.bot.services.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class IncomeMessageProcessor {

    @Autowired
    private LoggerService log;

    @Autowired
    private BotService botService;

    @Autowired
    private MessageManager messageManager;

    public void process(Message message) {
        log.debug("Message: {}", message);
        Long chatId = message.getChatId();
        String messageText = message.getText();
        User user = message.getFrom();

        if (messageText != null){

            SendMessage sendMessage = null;

            if (!messageText.startsWith("/")){
                botService.userLastMessage.put(user.getId(),messageText);
            }

            if (messageText.startsWith(Commands.START.getName())){
                messageManager.sendGreetingMessage(chatId, user);
            }

            if (messageText.startsWith(Commands.TRANSLATE.getName())){
                messageManager.translate(chatId, user);
            }
        }





    }
}
