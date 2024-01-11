package shesh.tron.utils.logger.impl;

import shesh.tron.utils.logger.Logger;
import shesh.tron.utils.logger.listener.LoggerListener;

import java.time.LocalDateTime;

public class TimestampLoggerDecorator implements Logger {

    private final Logger logger;

    public TimestampLoggerDecorator(Logger logger) {

        super();
        this.logger = logger;
    }

    @Override
    public void log(String message) {

        logger.log(getTimestamp() + " " + message);
    }

    @Override
    public void log(String message, Throwable throwable) {

        logger.log(getTimestamp() + " " + message, throwable);
    }

    @Override
    public void log(Throwable throwable) {

        logger.log(getTimestamp(), throwable);
    }

    @Override
    public void debug(String message) {

        logger.debug(getTimestamp() + " " + message);
    }

    @Override
    public void debug(String message, Throwable throwable) {

        logger.debug(getTimestamp() + " " + message, throwable);
    }

    @Override
    public void debug(Throwable throwable) {

        logger.debug(getTimestamp(), throwable);
    }

    @Override
    public void info(String message) {

        logger.info(getTimestamp() + " " + message);
    }

    @Override
    public void info(String message, Throwable throwable) {

        logger.info(getTimestamp() + " " + message, throwable);
    }

    @Override
    public void info(Throwable throwable) {

        logger.info(getTimestamp(), throwable);
    }

    @Override
    public void warn(String message) {

        logger.warn(getTimestamp() + " " + message);
    }

    @Override
    public void warn(String message, Throwable throwable) {

        logger.warn(getTimestamp() + " " + message, throwable);
    }

    @Override
    public void warn(Throwable throwable) {

        logger.warn(getTimestamp(), throwable);
    }

    @Override
    public void error(String message) {

        logger.error(getTimestamp() + " " + message);
    }

    @Override
    public void error(String message, Throwable throwable) {

        logger.error(getTimestamp() + " " + message, throwable);
    }

    @Override
    public void error(Throwable throwable) {

        logger.error(getTimestamp(), throwable);
    }

    @Override
    public void fatal(String message) {

        logger.fatal(getTimestamp() + " " + message);
    }

    @Override
    public void fatal(String message, Throwable throwable) {

        logger.fatal(getTimestamp() + " " + message, throwable);
    }

    @Override
    public void fatal(Throwable throwable) {

        logger.fatal(getTimestamp(), throwable);
    }

    @Override
    public void trace(String message) {

        logger.trace(getTimestamp() + " " + message);
    }

    @Override
    public void trace(String message, Throwable throwable) {

        logger.trace(getTimestamp() + " " + message, throwable);
    }

    @Override
    public void trace(Throwable throwable) {

        logger.trace(getTimestamp(), throwable);
    }

    private static String getTimestamp() {

        return LocalDateTime.now().toString();
    }

    @Override
    public void addListener(LoggerListener listener) {

        logger.addListener(listener);
    }

    @Override
    public void removeListener(LoggerListener listener) {

        logger.removeListener(listener);
    }

    @Override
    public void removeAllListeners() {

        logger.removeAllListeners();
    }
}
