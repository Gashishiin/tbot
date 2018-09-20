package com.randominfo.bot.commands;

import com.randominfo.bot.BeanUtil;
import com.randominfo.bot.entity.WeatherInfo;
import com.randominfo.bot.services.WeatherService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ShowWeatherCommand implements CommandExecutor {

    @Override
    public SendMessage execute(Message message) {
        Integer userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        WeatherService weatherService = BeanUtil.getBean(WeatherService.class);
        WeatherInfo weatherInfo =  weatherService.getWeatherInfo(message);
        if (weatherInfo == null){
            return weatherService.askForCity(userId, chatId);
        } else {
            sendMessage = weatherService.getWeather(weatherInfo,sendMessage);
        }
        return sendMessage;
    }
}
