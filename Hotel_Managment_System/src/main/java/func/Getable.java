package func;

import java.util.List;

public interface Getable<T> {
    T getById(int id);
    List<T> getAll();
}
