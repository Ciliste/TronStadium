package shesh.tron.utils.struct;

public interface Couple<T, V> {

    public abstract T getFirst();
    public abstract V getSecond();
    public abstract void setFirst(T first);
    public abstract void setSecond(V second);
}
