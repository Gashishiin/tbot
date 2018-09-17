package com.randominfo.bot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


public interface CommandExecutor {
    SendMessage execute(Message message);
}
