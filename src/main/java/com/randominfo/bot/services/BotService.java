package com.randominfo.bot.services;

import com.randominfo.bot.processors.IncomeMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;


@Service
public class BotService extends TelegramLongPollingBot{

    @Autowired
    private LoggerService log;

    @PostConstruct
    private void init(){
        log.debug("Request Config: {}", getOptions().getRequestConfig());
    }

    @Value("${token.key}")
    private String token;

    @Autowired
    private IncomeMessageProcessor messageProcessor;

    public void onUpdateReceived(Update update) {
        log.debug("Update: {}", update);
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
