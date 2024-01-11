package shesh.tron.worker;

import shesh.tron.utils.logger.LoggerFactory;
import shesh.tron.worker.request.Request;
import shesh.tron.worker.task.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerThread extends Thread {

    private static final int MAX_THREADS = 4;

    private static final WorkerThread instance = new WorkerThread();

    public static WorkerThread getInstance() {

        if (!instance.isAlive()) {

            instance.start();
        }

        return instance;
    }

    private boolean running = true;

    public WorkerThread() {

        super("Worker Thread");
    }

    private final AtomicInteger threadCount = new AtomicInteger(0);

    @Override
    public void run() {

        while (running) {

            Task task;

            synchronized (pendingTasks) {

                task = pendingTasks.poll();
            }

            if (task != null) {

                if (threadCount.get() < MAX_THREADS) {

                    synchronized (threadCount) {

                        threadCount.incrementAndGet();
                    }

                    new Thread(() -> {

                        try {

                            task.execute();
                        }
                        catch (Throwable throwable) {

                            LoggerFactory.getLogger().error("Error executing task", throwable);
                        }

                        synchronized (threadCount) {

                            threadCount.decrementAndGet();
                        }

                    }).start();
                }
                else {

                    task.executeAsync();
                }
            }
        }
    }

    private final LinkedList<Task> pendingTasks = new LinkedList<>();

    public void addTask(Task task) {

        synchronized (pendingTasks) {

            pendingTasks.add(task);
        }
    }
}
