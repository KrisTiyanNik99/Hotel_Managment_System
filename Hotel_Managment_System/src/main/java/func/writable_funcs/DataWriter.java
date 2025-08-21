package func.writable_funcs;

public interface DataWriter<T> extends Updater<T> {
    void createValue(T instance);
    void deleteById(Integer id);
}
