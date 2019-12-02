package de.upb.codingpirates.battleships.android.logger;

import android.support.log4j.LogConfigurator;

import org.apache.log4j.Level;

public class Log4jConfiguration {

    public static void init(){
        final LogConfigurator log = new LogConfigurator("battleship-android");

        log.setRootLevel(Level.ALL);
        log.setUseLogCatAppender(true);
        log.setUseFileAppender(true);
        log.setDailyRollingMode(true);
        log.configure();
    }
}
