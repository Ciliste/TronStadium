package shesh.tron.server;

import shesh.tron.utils.logger.LoggerFactory;
import shesh.tron.utils.logger.impl.ServerLogger;
import shesh.tron.utils.logger.impl.StackTraceLoggerDecorator;
import shesh.tron.utils.logger.impl.TerminalLoggerDecorator;
import shesh.tron.utils.logger.impl.TimestampLoggerDecorator;

import java.time.LocalDateTime;

public interface TronServer {

    public abstract void start();

    static void main(String[] args) throws Exception {

        LocalDateTime now = LocalDateTime.now();
        String date = now.toString().substring(0, now.toString().indexOf('T'));

        LoggerFactory.setLogger(new StackTraceLoggerDecorator(new TimestampLoggerDecorator(new TerminalLoggerDecorator(new ServerLogger("logs/" + date + ".log")))));

        TronServer tronServer = new TronServerImpl();
        tronServer.start();
    }
}
