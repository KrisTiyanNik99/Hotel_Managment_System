package func.writable_funcs;

/**
 * Represents a simple contract for updating existing data entities.
 *
 * @param <T> the type of entity being updated
 */
public interface Updater<T> {

    /**
     * Updates the value of an existing entity.
     *
     * @param instance the entity to be updated
     */
    void updateValue(T instance);
}
