package func;

public interface DataWriter<T> {
    void createValue(T instance);
    void deleteById(int id);
    void updateValue(T instance);
}
