package func;

import java.util.List;

public interface DataProvider<T> {
    T findById(int id);
    List<T> findAll();
}
