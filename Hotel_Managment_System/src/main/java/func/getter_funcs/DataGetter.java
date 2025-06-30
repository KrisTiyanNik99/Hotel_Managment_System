package func.getter_funcs;

import java.util.List;

public interface DataGetter<T> {
    T getById(int id);
    List<T> getAll();
}
