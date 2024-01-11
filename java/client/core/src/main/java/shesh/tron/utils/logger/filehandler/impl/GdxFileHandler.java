package shesh.tron.utils.logger.filehandler.impl;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import shesh.tron.utils.logger.filehandler.LoggerFileHandler;

public class GdxFileHandler implements LoggerFileHandler {

    private final String path;
    private FileHandle fileHandle;

    public GdxFileHandler(String path) {

        this.path = path;
    }

    @Override
    public void open() {

        fileHandle = new FileHandle(path);
    }

    @Override
    public void close() {

        fileHandle = null;
    }

    @Override
    public void write(String message) {

        fileHandle.writeString(message + "\n", true);
    }
}
