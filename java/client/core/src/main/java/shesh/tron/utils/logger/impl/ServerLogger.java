package shesh.tron.utils.logger.impl;

import shesh.tron.utils.logger.filehandler.LoggerFileHandler;
import shesh.tron.utils.logger.filehandler.impl.DefaultJavaFileHandle;

public class ServerLogger extends AbstractLogger {

    public ServerLogger(String logPath) {

        super(new DefaultJavaFileHandle(logPath));
    }
}
