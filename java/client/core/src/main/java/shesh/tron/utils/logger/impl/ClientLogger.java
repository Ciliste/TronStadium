package shesh.tron.utils.logger.impl;

import shesh.tron.utils.logger.filehandler.impl.GdxFileHandler;

public class ClientLogger extends AbstractLogger {

    public ClientLogger(String filePath) {

        super(new GdxFileHandler(filePath));
    }
}
