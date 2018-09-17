package com.randominfo.bot.services;

import com.randominfo.bot.MessageManager;
import com.randominfo.bot.commands.ShowWeatherCommand;
import com.randominfo.bot.entity.WeatherInfo;
import com.randominfo.bot.repository.WeatherInfoRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {
    @Autowired
    private WeatherInfoRepo weatherInfoRepo;

    @Autowired
    private LoggerService log;

    @Autowired
    private JsonResponseService jsonResponseService;

    @Autowired
    private MessageManager messageManager;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    private final String baseUrl = "http://api.openweathermap.org/data/2.5/weather";

    public Map<Long, Map<Integer, Boolean>> waitForInput = new HashMap<>();

    public SendMessage askForCity(Integer userId, Long chatId) {
        SendMessage sendMessage = new SendMessage(
                chatId,
                "Введите название города");
        if (waitForInput.containsKey(chatId)) {
            waitForInput.get(chatId).put(userId, true);
        } else {
            Map<Integer, Boolean> map = new HashMap<>();
            map.put(userId, true);
            waitForInput.put(chatId, map);
        }
        return sendMessage;
    }

    public SendMessage setWeatherOptions(Message message) {
        String messageText = message.getText();
        Integer userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        String cityName;
        Integer cityId;
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        try {
            String city = URLEncoder.encode(messageText, "UTF-8");
            String urlString = String.format("%s?q=%s&appid=%s&units=metric", baseUrl, city, weatherApiKey);
            URL url = new URL(urlString);
            JSONObject response = jsonResponseService.getJsonObject(url);
            log.debug("JSON for weather setting: {}", response);
            cityName = response.getString("name");
            cityId = response.getInt("id");
            WeatherInfo weatherInfo = new WeatherInfo(userId, cityName, cityId);
            weatherInfoRepo.save(weatherInfo);
            sendMessage.setText(String.format("Город установлен: %s", cityName));
        } catch (IOException e) {
            log.error("Can not define city", e);
            sendMessage.setText("Не могу установить город для получения погоды :(");
        }
        return  sendMessage;
    }

    public WeatherInfo getWeatherInfo(Message message){
        Integer userId = message.getFrom().getId();
        return weatherInfoRepo.findByUserId(userId);
    }

    public String getWeather(WeatherInfo weatherInfo) {
        log.debug("Weather Info: {}", weatherInfo);
        String urlString = String.format("%s?id=%d&appid=%s&units=metric", baseUrl, weatherInfo.getCityId(), weatherApiKey);
        log.debug("Weather URL: {}", urlString);
        String weatherText;
        try {
            URL url = new URL(urlString);
            JSONObject jsonObject = jsonResponseService.getJsonObject(url);
            log.debug("Json Weather: {}", jsonObject);
            JSONObject main = jsonObject.getJSONObject("main");
            int temp = main.getInt("temp");
            weatherText = String.format("Город: %s, Температура: %d", weatherInfo.getCityName(), temp);
        } catch (MalformedURLException e) {
            log.error("Problem during getting weather", e);
            weatherText = "Не смог определить погоду :(";
        } catch (IOException e) {
            log.error("Problem during weather parsing and decoding", e);
            weatherText = "Не смог определить погоду";
        }
        return weatherText;
    }
}
