package shesh.tron.worker.task;

public interface Task {

    public abstract void execute();

    public abstract void executeAsync();
}
