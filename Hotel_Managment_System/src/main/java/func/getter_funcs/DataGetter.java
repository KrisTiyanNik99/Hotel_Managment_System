package func.getter_funcs;

import java.util.List;

public interface DataGetter<T> {
    T getById(Integer id);
    List<T> getAll();
}
