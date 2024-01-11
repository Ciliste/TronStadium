package shesh.tron.utils.logger.impl;

import shesh.tron.utils.logger.Logger;
import shesh.tron.utils.logger.listener.LoggerListener;

/**
 * Logger decorator for logging to the terminal.
 */
public class TerminalLoggerDecorator implements Logger {

    private final Logger logger;

    public TerminalLoggerDecorator(Logger logger) {

        super();
        this.logger = logger;

        logger.addListener(new LoggerListener() {

            @Override
            public void onLog(String message) {

                System.out.println(message);
            }

            @Override
            public void onLog(String message, Throwable throwable) {

                System.out.println(message);
                throwable.printStackTrace();
            }

            @Override
            public void onLog(Throwable throwable) {

                throwable.printStackTrace();
            }

            @Override
            public void onDebug(String message) {

                System.out.println(message);
            }

            @Override
            public void onDebug(String message, Throwable throwable) {

                System.out.println(message);
                throwable.printStackTrace();
            }

            @Override
            public void onDebug(Throwable throwable) {

                throwable.printStackTrace();
            }

            @Override
            public void onInfo(String message) {

                System.out.println(message);
            }

            @Override
            public void onInfo(String message, Throwable throwable) {

                System.out.println(message);
                throwable.printStackTrace();
            }

            @Override
            public void onInfo(Throwable throwable) {

                throwable.printStackTrace();
            }

            @Override
            public void onWarn(String message) {

                System.out.println(message);
            }

            @Override
            public void onWarn(String message, Throwable throwable) {

                System.out.println(message);
                throwable.printStackTrace();
            }

            @Override
            public void onWarn(Throwable throwable) {

                throwable.printStackTrace();
            }

            @Override
            public void onError(String message) {

                System.out.println(message);
            }

            @Override
            public void onError(String message, Throwable throwable) {

                System.out.println(message);
                throwable.printStackTrace();
            }

            @Override
            public void onError(Throwable throwable) {

                throwable.printStackTrace();
            }

            @Override
            public void onFatal(String message) {

                System.out.println(message);
            }

            @Override
            public void onFatal(String message, Throwable throwable) {

                System.out.println(message);
                throwable.printStackTrace();
            }

            @Override
            public void onFatal(Throwable throwable) {

                throwable.printStackTrace();
            }

            @Override
            public void onTrace(String message) {

                System.out.println(message);
            }

            @Override
            public void onTrace(String message, Throwable throwable) {

                System.out.println(message);
                throwable.printStackTrace();
            }

            @Override
            public void onTrace(Throwable throwable) {

                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void log(String message) {

        logger.log(message);
    }

    @Override
    public void log(String message, Throwable throwable) {

        logger.log(message, throwable);
    }

    @Override
    public void log(Throwable throwable) {

        logger.log(throwable);
    }

    @Override
    public void debug(String message) {

        logger.debug(message);
    }

    @Override
    public void debug(String message, Throwable throwable) {

        logger.debug(message, throwable);
    }

    @Override
    public void debug(Throwable throwable) {

        logger.debug(throwable);
    }

    @Override
    public void info(String message) {

        logger.info(message);
    }

    @Override
    public void info(String message, Throwable throwable) {

        logger.info(message, throwable);
    }

    @Override
    public void info(Throwable throwable) {

        logger.info(throwable);
    }

    @Override
    public void warn(String message) {

        logger.warn(message);
    }

    @Override
    public void warn(String message, Throwable throwable) {

        logger.warn(message, throwable);
    }

    @Override
    public void warn(Throwable throwable) {

        logger.warn(throwable);
    }

    @Override
    public void error(String message) {

        logger.error(message);
    }

    @Override
    public void error(String message, Throwable throwable) {

        logger.error(message, throwable);
    }

    @Override
    public void error(Throwable throwable) {

        logger.error(throwable);
    }

    @Override
    public void fatal(String message) {

        logger.fatal(message);
    }

    @Override
    public void fatal(String message, Throwable throwable) {

        logger.fatal(message, throwable);
    }

    @Override
    public void fatal(Throwable throwable) {

        logger.fatal(throwable);
    }

    @Override
    public void trace(String message) {

        logger.trace(message);
    }

    @Override
    public void trace(String message, Throwable throwable) {

        logger.trace(message, throwable);
    }

    @Override
    public void trace(Throwable throwable) {

        logger.trace(throwable);
    }

    @Override
    public void addListener(LoggerListener listener) {

        logger.addListener(listener);
    }

    @Override
    public void removeListener(LoggerListener listener) {

        logger.removeListener(listener);
    }

    @Override
    public void removeAllListeners() {

        logger.removeAllListeners();
    }
}
