package shesh.tron.utils.logger;

import shesh.tron.utils.logger.listener.LoggerListenable;

public interface Logger extends LoggerListenable {

    public abstract void log(String message);
    public abstract void log(String message, Throwable throwable);
    public abstract void log(Throwable throwable);

    public abstract void debug(String message);
    public abstract void debug(String message, Throwable throwable);
    public abstract void debug(Throwable throwable);

    public abstract void info(String message);
    public abstract void info(String message, Throwable throwable);
    public abstract void info(Throwable throwable);

    public abstract void warn(String message);
    public abstract void warn(String message, Throwable throwable);
    public abstract void warn(Throwable throwable);

    public abstract void error(String message);
    public abstract void error(String message, Throwable throwable);
    public abstract void error(Throwable throwable);

    public abstract void fatal(String message);
    public abstract void fatal(String message, Throwable throwable);
    public abstract void fatal(Throwable throwable);

    public abstract void trace(String message);
    public abstract void trace(String message, Throwable throwable);
    public abstract void trace(Throwable throwable);
}
