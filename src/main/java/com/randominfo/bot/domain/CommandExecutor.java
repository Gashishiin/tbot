package com.randominfo.bot.domain;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


public interface CommandExecutor {
    SendMessage execute(Message message);
}
