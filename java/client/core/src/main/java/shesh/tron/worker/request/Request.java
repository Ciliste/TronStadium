package shesh.tron.worker.request;

import shesh.tron.worker.WorkerThread;
import shesh.tron.worker.response.Response;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

public abstract class Request {

    private final Collection<Consumer<Response>> consumers = new LinkedList<>();

    public Request then(Consumer<Response> consumer) {

        consumers.add(consumer);
        return this;
    }

    public Request then(Runnable runnable) {

        consumers.add(response -> runnable.run());
        return this;
    }

    private final Collection<Consumer<Exception>> errorConsumers = new LinkedList<>();

    public Request catchError(Consumer<Exception> consumer) {

        errorConsumers.add(consumer);
        return this;
    }

    public void executeAsync() {

        WorkerThread.getInstance().addTask(this);
    }

    void notifyConsumers(Response response) {

        consumers.forEach(consumer -> consumer.accept(response));
    }

    void notifyErrorConsumers(Exception exception) {

        errorConsumers.forEach(consumer -> consumer.accept(exception));
    }

    public abstract void execute();
}
