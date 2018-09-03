package com.randominfo.bot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class BotService extends TelegramLongPollingBot{

    @Value("${token.key}")
    private String token;

    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            SendMessage sendMessage = new SendMessage()
                    .setText("Hello");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return "My Bot";
    }

    public String getBotToken() {
        return token;
    }
}
