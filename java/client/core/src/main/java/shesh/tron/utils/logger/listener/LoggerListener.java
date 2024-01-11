package shesh.tron.utils.logger.listener;

public interface LoggerListener {

    public abstract void onLog(String message);
    public abstract void onLog(String message, Throwable throwable);
    public abstract void onLog(Throwable throwable);

    public abstract void onDebug(String message);
    public abstract void onDebug(String message, Throwable throwable);
    public abstract void onDebug(Throwable throwable);

    public abstract void onInfo(String message);
    public abstract void onInfo(String message, Throwable throwable);
    public abstract void onInfo(Throwable throwable);

    public abstract void onWarn(String message);
    public abstract void onWarn(String message, Throwable throwable);
    public abstract void onWarn(Throwable throwable);

    public abstract void onError(String message);
    public abstract void onError(String message, Throwable throwable);
    public abstract void onError(Throwable throwable);

    public abstract void onFatal(String message);
    public abstract void onFatal(String message, Throwable throwable);
    public abstract void onFatal(Throwable throwable);

    public abstract void onTrace(String message);
    public abstract void onTrace(String message, Throwable throwable);
    public abstract void onTrace(Throwable throwable);
}
