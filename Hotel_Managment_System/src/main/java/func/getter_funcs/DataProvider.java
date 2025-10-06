package func.getter_funcs;

import java.util.List;

/**
 * Defines a more complete data access interface than {@link DataGetter},
 * typically implemented by repository or data service layers that interact
 * with persistent storage (e.g., files, databases).
 *
 * @param <T> the type of entity being managed
 */
public interface DataProvider<T> {

    /**
     * Finds an entity by its identifier.
     *
     * @param id the entity ID
     * @return the entity instance, or {@code null} if not found
     */
    T findById(Integer id);

    /**
     * Retrieves all persisted entities.
     *
     * @return a list of all entities
     */
    List<T> findAll();
}
