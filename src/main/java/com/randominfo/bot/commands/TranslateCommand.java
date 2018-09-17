package com.randominfo.bot.commands;

import com.randominfo.bot.BeanUtil;
import com.randominfo.bot.services.TranslateService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class TranslateCommand implements CommandExecutor {

    @Override
    public SendMessage execute(Message message) {
        SendMessage sendMessage = new SendMessage().setChatId(message.getChatId());
        String translatedText;
        try {
            TranslateService translateService = BeanUtil.getBean(TranslateService.class);
            translatedText = translateService.translate(message);
            sendMessage.setText(translatedText);
        } catch (Exception e) {
            sendMessage.setText("Не смог перевести :(");
        }
        return sendMessage;
    }
}
