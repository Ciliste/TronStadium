package shesh.tron.worker;

import shesh.tron.worker.request.Request;

import java.util.LinkedList;
import java.util.List;

public class WorkerThread extends Thread {

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

    @Override
    public void run() {

        while (running) {

            try {

                if (!pendingRequests.isEmpty()) {

                    List<Request> requests = new LinkedList<>();

                    synchronized (pendingRequests) {

                        requests.addAll(pendingRequests);

                        pendingRequests.clear();
                    }

                    for (Request request : requests) {

                        request.execute();
                    }
                }

                Thread.sleep(1000);
            }
            catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    private final LinkedList<Request> pendingRequests = new LinkedList<>();

    public void addTask(Request request) {

        synchronized (pendingRequests) {

            pendingRequests.add(request);
        }
    }
}
