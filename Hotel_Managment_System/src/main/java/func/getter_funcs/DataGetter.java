package func.getter_funcs;

import java.util.List;

/**
 * Defines a minimal read-only contract for accessing data objects.
 * Typically, used for lightweight or cached data access layers that do not
 * handle persistence directly.
 *
 * @param <T> the type of entity to be retrieved
 */
public interface DataGetter<T> {

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id the entity ID
     * @return the entity instance, or {@code null} if not found
     */
    T getById(Integer id);

    /**
     * Returns all entities currently available.
     *
     * @return a list of all entities
     */
    List<T> getAll();
}
