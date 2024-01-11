package shesh.tron.utils.logger.listener;

public interface LoggerListenable {

    public abstract void addListener(LoggerListener listener);
    public abstract void removeListener(LoggerListener listener);
    public abstract void removeAllListeners();
}
