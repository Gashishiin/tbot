package com.randominfo.bot.domain;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public class GreetUserCommand implements CommandExecutor {

    @Override
    public SendMessage execute(Message message) {
        User user = message.getFrom();
        Long chatId = message.getChatId();
        String firstName = user.getFirstName();
        String greeting;
        if (firstName != null) {
            greeting = "Hello " + firstName + "!";
        } else {
            greeting = "Hello!";
        }
        return new SendMessage(chatId,greeting);
    }
}

