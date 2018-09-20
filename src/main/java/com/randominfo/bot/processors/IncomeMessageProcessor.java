package com.randominfo.bot.processors;

import com.randominfo.bot.MessageManager;
import com.randominfo.bot.commands.*;
import com.randominfo.bot.services.BotService;
import com.randominfo.bot.services.LoggerService;
import com.randominfo.bot.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class IncomeMessageProcessor {

    @Autowired
    private LoggerService log;

    @Autowired
    private BotService botService;

    @Autowired
    private CommandManager commandManager;    

    @Autowired
    private WeatherService weatherService;

    public void process(Message message) {
        CommandExecutor command = null;        
        log.debug("Chat: {}, User: {}, Text: {}", message.getChatId(), message.getFrom(), message.getText());
        String messageText = message.getText();
        User user = message.getFrom();

        if (messageText != null) {
            Map<Integer, Boolean> waitForInput = weatherService
                    .waitForInput
                    .get(message.getChatId());

            if (waitForInput != null) {
                Boolean isWaitingForInput = waitForInput.get(user.getId());
                if (Boolean.TRUE.equals(isWaitingForInput)) {
                    command = new SetCityCommand();
                    waitForInput.remove(user.getId());
                }
            }
            if (!messageText.startsWith("/")) {
                Map<Integer, String> lastMessages = botService.userLastMessage.get(message.getChatId());
                if (lastMessages == null) {
                    lastMessages = new HashMap<>();
                    lastMessages.put(user.getId(), messageText);
                    botService.userLastMessage.put(message.getChatId(), lastMessages);
                } else {
                    lastMessages.put(user.getId(), messageText);
                }
            }
                    
            
            
            if (messageText.startsWith(CommandEnum.START.getName())) {
                command = new GreetUserCommand();
            }

            if (messageText.startsWith(CommandEnum.TRANSLATE.getName())) {
                command = new TranslateCommand();
            }

            if (messageText.startsWith(CommandEnum.WEATHER.getName())){
                command = new ShowWeatherCommand();
            }

            if (messageText.startsWith(CommandEnum.CHANGE_CITY.getName())){
                command = new ChangeCityCommand();
            }
            
            if (command != null){
                commandManager.execute(command, message);    
            }
        }
    }
}
