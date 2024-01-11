package shesh.tron.worker.request.impl;

import shesh.tron.worker.WorkerThread;
import shesh.tron.worker.request.Request;
import shesh.tron.worker.task.impl.AbstractTask;

import java.util.function.Consumer;

public abstract class AbstractRequest<T> extends AbstractTask implements Request<T> {

    private Consumer<T> then;

    @Override
    public Request<T> then(Consumer<T> consumer) {

        then = consumer;

        return this;
    }

    private Consumer<Throwable> error;

    @Override
    public Request<T> error(Consumer<Throwable> consumer) {

        error = consumer;

        return this;
    }

    protected void callThen(T result) {

        if (then != null) {

            then.accept(result);
        }
    }

    protected void callError(Throwable throwable) {

        if (error != null) {

            error.accept(throwable);
        }
    }
}
