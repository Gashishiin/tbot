package com.randominfo.bot;

import com.randominfo.bot.services.BotService;
import com.randominfo.bot.services.LoggerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class MessageManager {

    @Autowired
    private LoggerService log;

    @Autowired
    private BotService botService;

    private final String baseYAPIURL = "https://translate.yandex.net/api/v1.5/tr.json/translate";

    private final String lang = "ru-en";

    @Value("${yandex.api.key}")
    private String apiKey;

    public SendMessage sendGreetingMessage(Long chatId, User user) {
        String firstName = user.getFirstName();
        String greeting;
        if (firstName != null) {
            greeting = "Hello " + firstName + "!";
        } else {
            greeting = "Hello!";
        }

        SendMessage sendMessage = new SendMessage()
                .setText(greeting)
                .setChatId(chatId);
        execute(sendMessage);
        return sendMessage;
    }

    public SendMessage translate(Long chatId, User user) {
        String text = botService.userLastMessage.get(user.getId());
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId);

        log.debug("Last message: {}", text);


        try {
            StringBuilder urlString = new StringBuilder(baseYAPIURL)
                    .append("?key=")
                    .append(apiKey)
                    .append("&lang=")
                    .append(lang)
                    .append("&text=")
                    .append(URLEncoder.encode(text, "UTF-8"));
            URL url = new URL(urlString.toString());
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

            JSONArray jsonArray = (JSONArray) jsonObject.get("text");
            String message = jsonArray.join(" ");;
            sendMessage.setText(message);
            execute(sendMessage);
            return sendMessage;

        } catch (Exception e) {
            log.error("Can not get translation", e);
        }


        sendMessage.setText("Не смог перевести :(");

        execute(sendMessage);
        return sendMessage;

    }

    private void execute(SendMessage sendMessage) {
        try {
            botService.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Can not send message: ", e);
        }
    }
}
