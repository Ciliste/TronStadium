package shesh.tron.utils.logger.filehandler.impl;

import shesh.tron.utils.logger.Logger;
import shesh.tron.utils.logger.filehandler.LoggerFileHandler;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultJavaFileHandle implements LoggerFileHandler {

    private final String path;
    private File file;
    private FileWriter fileWriter;

    public DefaultJavaFileHandle(String path) {

        Path p = Paths.get(path);

        if (!p.isAbsolute()) {

            path = System.getProperty("user.dir") + "/" + path;
        }

        this.path = path;
    }

    @Override
    public void open() {

        file = new File(path);

        if (!file.exists()) {

            try {

                File dirs = file.getParentFile();
                boolean created = dirs.mkdirs();

                if (!created) {

                    throw new Exception("Failed to create directory");
                }

                created = file.createNewFile();

                if (!created) {

                    throw new Exception("Failed to create file");
                }
            }
            catch (Exception e) {

                e.printStackTrace();
            }
        }

        try {

            fileWriter = new FileWriter(file, true);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void close() {

        file = null;
        try {

            fileWriter.close();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        finally {

            fileWriter = null;
        }
    }

    @Override
    public void write(String message) {

        try {

            fileWriter.write(message + "\n");
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }
}
