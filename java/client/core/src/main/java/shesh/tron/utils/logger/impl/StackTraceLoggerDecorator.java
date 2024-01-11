package shesh.tron.utils.logger.impl;

import shesh.tron.utils.logger.Logger;
import shesh.tron.utils.logger.listener.LoggerListener;

/**
 * Logger decorator for logging stack traces.
 */
public class StackTraceLoggerDecorator implements Logger {

    private final Logger logger;

    public StackTraceLoggerDecorator(Logger logger) {

        super();
        this.logger = logger;
    }

    @Override
    public void log(String message) {

        logger.log(getStackTrace() + "\n" + message);
    }

    @Override
    public void log(String message, Throwable throwable) {

        logger.log(getStackTrace() + "\n" + message, throwable);
    }

    @Override
    public void log(Throwable throwable) {

        logger.log(getStackTrace(), throwable);
    }

    @Override
    public void debug(String message) {

        logger.debug(getStackTrace() + "\n" + message);
    }

    @Override
    public void debug(String message, Throwable throwable) {

        logger.debug(getStackTrace() + "\n" + message, throwable);
    }

    @Override
    public void debug(Throwable throwable) {

        logger.debug(getStackTrace(), throwable);
    }

    @Override
    public void info(String message) {

        logger.info(getStackTrace() + "\n" + message);
    }

    @Override
    public void info(String message, Throwable throwable) {

        logger.info(getStackTrace() + "\n" + message, throwable);
    }

    @Override
    public void info(Throwable throwable) {

        logger.info(getStackTrace(), throwable);
    }

    @Override
    public void warn(String message) {

        logger.warn(getStackTrace() + "\n" + message);
    }

    @Override
    public void warn(String message, Throwable throwable) {

        logger.warn(getStackTrace() + "\n" + message, throwable);
    }

    @Override
    public void warn(Throwable throwable) {

        logger.warn(getStackTrace(), throwable);
    }

    @Override
    public void error(String message) {

        logger.error(getStackTrace() + "\n" + message);
    }

    @Override
    public void error(String message, Throwable throwable) {

        logger.error(getStackTrace() + "\n" + message, throwable);
    }

    @Override
    public void error(Throwable throwable) {

        logger.error(getStackTrace(), throwable);
    }

    @Override
    public void fatal(String message) {

        logger.fatal(getStackTrace() + "\n" + message);
    }

    @Override
    public void fatal(String message, Throwable throwable) {

        logger.fatal(getStackTrace() + "\n" + message, throwable);
    }

    @Override
    public void fatal(Throwable throwable) {

        logger.fatal(getStackTrace(), throwable);
    }

    @Override
    public void trace(String message) {

        logger.trace(getStackTrace() + "\n" + message);
    }

    @Override
    public void trace(String message, Throwable throwable) {

        logger.trace(getStackTrace() + "\n" + message, throwable);
    }

    @Override
    public void trace(Throwable throwable) {

        logger.trace(getStackTrace(), throwable);
    }

    private static String getStackTrace() {

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        StackTraceElement element;
        for (int i = 1; i < stackTraceElements.length; i++) {

            if (!stackTraceElements[i].getClassName().contains("shesh.tron.utils.logger")) {

                element = stackTraceElements[i];
                return element.toString();
            }
        }

        return "";
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
