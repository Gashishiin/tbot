package com.randominfo.bot.repository;

import com.randominfo.bot.entity.WeatherInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface WeatherInfoRepo  extends CrudRepository<WeatherInfo, Integer>{

    WeatherInfo findByUserId(Integer userId);

    WeatherInfo save(WeatherInfo weatherInfo);

}
