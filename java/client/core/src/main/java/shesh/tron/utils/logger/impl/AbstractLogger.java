package shesh.tron.utils.logger.impl;

import shesh.tron.utils.logger.Logger;
import shesh.tron.utils.logger.filehandler.LoggerFileHandler;
import shesh.tron.utils.logger.listener.LoggerListener;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class AbstractLogger implements Logger {

    protected final LoggerFileHandler loggerFileHandler;

    protected AbstractLogger(LoggerFileHandler loggerFileHandler) {

        super();
        this.loggerFileHandler = loggerFileHandler;
    }

    @Override
    public void log(String message) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onLog(message));
    }

    @Override
    public void log(String message, Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onLog(message, throwable));
    }

    @Override
    public void log(Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onLog(throwable));
    }

    @Override
    public void debug(String message) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onDebug(message));
    }

    @Override
    public void debug(String message, Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onDebug(message, throwable));
    }

    @Override
    public void debug(Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onDebug(throwable));
    }

    @Override
    public void info(String message) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onInfo(message));
    }

    @Override
    public void info(String message, Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onInfo(message, throwable));
    }

    @Override
    public void info(Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onInfo(throwable));
    }

    @Override
    public void warn(String message) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onWarn(message));
    }

    @Override
    public void warn(String message, Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onWarn(message, throwable));
    }

    @Override
    public void warn(Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onWarn(throwable));
    }

    @Override
    public void error(String message) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onError(message));
    }

    @Override
    public void error(String message, Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onError(message, throwable));
    }

    @Override
    public void error(Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onError(throwable));
    }

    @Override
    public void fatal(String message) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onFatal(message));
    }

    @Override
    public void fatal(String message, Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onFatal(message, throwable));
    }

    @Override
    public void fatal(Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onFatal(throwable));
    }

    @Override
    public void trace(String message) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onTrace(message));
    }

    @Override
    public void trace(String message, Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(message);
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onTrace(message, throwable));
    }

    @Override
    public void trace(Throwable throwable) {

        loggerFileHandler.open();
        loggerFileHandler.write(throwable.getMessage());
        loggerFileHandler.close();

        listeners.forEach(listener -> listener.onTrace(throwable));
    }

    private final List<LoggerListener> listeners = new LinkedList<>();

    @Override
    public void addListener(LoggerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(LoggerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
    }
}
