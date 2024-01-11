package shesh.tron.screen.context;

public interface Context {

    public void set(String key, Object value);
    public Object get(String key);
}
