package com.randominfo.bot.services;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class JsonResponseService {
    public JSONObject getJsonObject(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String inputStr;
        StringBuilder responseStrBuilder = new StringBuilder();
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        streamReader.close();
        return new JSONObject(responseStrBuilder.toString());
    }
}
