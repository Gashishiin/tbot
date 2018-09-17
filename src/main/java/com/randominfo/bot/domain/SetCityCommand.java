package com.randominfo.bot.domain;

import com.randominfo.bot.BeanUtil;
import com.randominfo.bot.services.WeatherService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class SetCityCommand implements CommandExecutor{
    @Override
    public SendMessage execute(Message message) {
        WeatherService weatherService = BeanUtil.getBean(WeatherService.class);
        return weatherService.setWeatherOptions(message);
    }
}
