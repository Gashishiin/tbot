package com.randominfo.bot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class LoggerService {

    @PostConstruct
    private void init(){
        logger.info("Logging started");
    }

    private  Logger logger = LoggerFactory.getLogger(LoggerService.class);

    public void debug(String message, Object ... args){
        logger.debug(message, args);
    }

    public void info(String message, Object ... args){
        logger.info(message, args);
    }

    public void error(String message, Object ... args){
        logger.error(message, args);
    }

}
