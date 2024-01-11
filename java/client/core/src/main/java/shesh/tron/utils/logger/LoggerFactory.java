package shesh.tron.utils.logger;

public class LoggerFactory {

    private static Logger logger;

    public static Logger getLogger() {

        return logger;
    }

    public static void setLogger(Logger logger) {

        LoggerFactory.logger = logger;
    }
}
