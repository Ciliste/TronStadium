package shesh.tron.worker.task.impl;

import shesh.tron.worker.WorkerThread;
import shesh.tron.worker.task.Task;

public abstract class AbstractTask implements Task {

    @Override
    public void executeAsync() {

        WorkerThread.getInstance().addTask(this);
    }
}
