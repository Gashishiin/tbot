package com.randominfo.bot.commands;

import com.randominfo.bot.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class CommandManager {

    @Autowired
    private MessageManager messageManager;

    public void execute(CommandExecutor commandExecutor, Message message){
        SendMessage sendMessage = commandExecutor.execute(message);
        messageManager.execute(sendMessage);
    }
}
