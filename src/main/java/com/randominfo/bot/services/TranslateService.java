package com.randominfo.bot.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

@Service
public class TranslateService {
    private final String baseYAPIURL = "https://translate.yandex.net/api/v1.5/tr.json/translate";

    private final String lang = "ru-en";

    @Value("${yandex.api.key}")
    private String apiKey;

    @Autowired
    private BotService botService;

    @Autowired
    private JsonResponseService jsonResponseService;

    public String translate(Message message) throws IOException {
        User user = message.getFrom();
        Long chatId = message.getChatId();
        Map<Integer, String> lastMessages = botService.userLastMessage.get(chatId);
        SendMessage sendMessage = new SendMessage().setChatId(chatId);

        String text;
        if (lastMessages == null){
            return "Нечего переводить :(";
        } else {
            text = lastMessages.get(user.getId());
        }

        String urlString = baseYAPIURL +
                "?key=" +
                apiKey +
                "&lang=" +
                lang +
                "&text=" +
                URLEncoder.encode(text, "UTF-8");
        URL url = new URL(urlString);
        JSONObject jsonObject = jsonResponseService.getJsonObject(url);
        JSONArray jsonArray = (JSONArray) jsonObject.get("text");
        return jsonArray.join(" ");

    }



}
