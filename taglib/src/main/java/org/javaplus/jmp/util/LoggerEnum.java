package org.javaplus.jmp.util;

import java.util.logging.Logger;

/**
 * An enumerations of {@link java.util.logging.Logger} instances used
 * by this library.
 * 
 * @author Stephen Suen
 */
public enum LoggerEnum implements Constants {

    ROOT(""),
    APPLICATION("application"),
    TAGLIB("taglib");
    private static final String LOGGER_NAME_PREFIX = "org.javaplus.jmp";
    private String loggerName = LOGGER_NAME_PREFIX;

    LoggerEnum(String loggerName) {
        if (!loggerName.isEmpty()) {
            this.loggerName = this.loggerName + "." + loggerName;
        }
    }

    public String getLoggerName() {
        return loggerName;
    }

    public Logger getLogger() {
        return Logger.getLogger(loggerName, RESOURCE_NAME);
    }
}
