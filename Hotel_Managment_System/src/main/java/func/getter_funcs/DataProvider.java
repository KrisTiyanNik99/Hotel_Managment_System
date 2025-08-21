package func.getter_funcs;

import java.util.List;

public interface DataProvider<T> {
    T findById(Integer id);
    List<T> findAll();
}
