package func.writable_funcs;

/**
 * Defines the basic write and delete operations for persistent data.
 * Extends {@link Updater} to include create and delete functionality.
 * Typically, used by repository classes that modify stored entities.
 *
 * @param <T> the type of entity to write or delete
 */
public interface DataWriter<T> extends Updater<T> {

    /**
     * Creates and persists a new entity instance.
     *
     * @param instance the entity to create
     */
    void createValue(T instance);

    /**
     * Deletes an entity by its identifier.
     *
     * @param id the entity ID
     */
    void deleteById(Integer id);
}
