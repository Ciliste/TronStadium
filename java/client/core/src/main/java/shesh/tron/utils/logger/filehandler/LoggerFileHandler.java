package shesh.tron.utils.logger.filehandler;

public interface LoggerFileHandler {

    public abstract void open();
    public abstract void close();

    public abstract void write(String message);
}
