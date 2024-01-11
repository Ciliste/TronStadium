package shesh.tron.worker.request;

import shesh.tron.worker.task.Task;

import java.util.function.Consumer;

public interface Request<T> extends Task {

    public abstract Request<T> then(Consumer<T> consumer);
    public abstract Request<T> error(Consumer<Throwable> consumer);
}
