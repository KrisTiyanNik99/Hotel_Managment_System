package func;

/**
 * Provides object creation functionality from parsed data arrays.
 * Typically, implemented by repository classes that read serialized data from files
 * and reconstruct entity instances.
 *
 * @param <T> the type of object being constructed
 */
public interface ObjectProvider<T> {

    /**
     * Creates an object from a given set of string data values.
     *
     * @param data the array containing object properties as strings
     * @return the reconstructed object instance
     */
    T getObjectFromData(String[] data);
}
