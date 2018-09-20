package com.randominfo.bot.services;

import java.util.HashMap;
import java.util.Map;

public class   WeatherEmoji {
    public final static Map<String, Integer> map = new HashMap<>();

    static{
        map.put("01d", 0x2600); //clear
        map.put("01n", 0x2600); //clear
        map.put("02d", 0x1F326); //little cloudy
        map.put("02n", 0x1F326); //little cloudy
        map.put("03d", 0x2601); //cloud
        map.put("03n", 0x2601); //cloud
        map.put("04d", 0x2601); //cloud
        map.put("04n", 0x2601); //cloud
        map.put("09n", 0x1F327); //rain
        map.put("09d", 0x1F327); //rain
        map.put("10d", 0x1F327); //rain
        map.put("10n", 0x1F327); //rain
        map.put("11d", 0x26C8); //thunder
        map.put("11n", 0x26C8); //thunder
        map.put("13d", 0x1F328); //snow
        map.put("13n", 0x1F328); //snow
        map.put("50d", 0x1F32B); //fog
        map.put("50n", 0x1F32B); //fog

    }




}
