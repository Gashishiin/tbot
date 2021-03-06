package com.randominfo.bot.services;

import com.randominfo.bot.processors.IncomeMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class BotService extends TelegramLongPollingBot{

    @Autowired
    private LoggerService log;

    @Value("${token.key}")
    private String token;

    @Autowired
    private IncomeMessageProcessor messageProcessor;

    public final Map<Long, Map<Integer, String>> userLastMessage = new ConcurrentHashMap<>();

    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            messageProcessor.process(update.getMessage());
        }
    }

    public String getBotUsername() {
        return "My Bot";
    }

    public String getBotToken() {
        return token;
    }
}
