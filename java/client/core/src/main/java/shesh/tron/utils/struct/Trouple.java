package shesh.tron.utils.struct;

public interface Trouple<T, V, K> extends Couple<T, V> {

    public abstract K getThird();
    public abstract void setThird(K third);
}
