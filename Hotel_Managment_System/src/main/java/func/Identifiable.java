package func;

/**
 * Defines a contract for uniquely identifiable objects that can be serialized
 * into a text format suitable for file-based persistence.
 */
public interface Identifiable {

    /**
     * Returns the unique identifier of the object.
     *
     * @return the unique ID
     */
    Integer getId();

    /**
     * Returns a text-based representation of the object,
     * usually used for saving data into text files.
     *
     * @return a formatted text representation
     */
    String textFormat();
}
