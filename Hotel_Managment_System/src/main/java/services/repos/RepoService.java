package services.repos;

import config.Configurations;
import func.getter_funcs.DataProvider;
import func.writable_funcs.DataWriter;
import func.Identifiable;
import func.ObjectProvider;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Abstract base class that provides a generic, file-based implementation
 * of a repository service.
 * {@code RepoService} serves as the bridge between persistent storage (text files)
 * and the in-memory representation of domain entities.
 * It implements:
 *   {@link ObjectProvider} – to reconstruct objects from serialized data
 *   {@link DataWriter} – to create, update, and delete entities
 *   {@link DataProvider} – to retrieve entities from storage
 *
 * @param <T> the type of entity managed by this repository, must implement {@link Identifiable}
 */
public abstract class RepoService<T extends Identifiable>
        implements ObjectProvider<T>, DataWriter<T>, DataProvider<T> {

    /** Message used when a requested element cannot be found. */
    private static final String ELEMENT_NOT_FOUND_MESSAGE = "Element with id: %d is not found!";

    /** Delimiter used to separate key-value pairs in serialized objects. */
    protected static final String REGEX_EXPRESSION = ":";

    /** Default index position for property values in split string data. */
    protected static final int VALUE_POSITION = 1;

    /** In-memory map of entity ID → entity instance. */
    private final Map<Integer, T> entityMap;

    /** The filename of the repository file. */
    private String repositoryFileName;

    /** The last generated entity ID. */
    private Integer newId;

    /**
     * Initializes the repository by loading data from the given file.
     *
     * @param repositoryFileName the name of the file to load data from;
     *                           if {@code null}, a new one will be created
     */
    public RepoService(String repositoryFileName) {
        setRepositoryFileName(repositoryFileName);
        entityMap = new LinkedHashMap<>();
        loadFromFile();
    }

    // -------------------- CRUD Operations --------------------

    @Override
    public T findById(Integer id) {
        return entityMap.get(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (!existsById(id)) {
            System.out.printf(ELEMENT_NOT_FOUND_MESSAGE + "%n", id);
            return;
        }
        entityMap.remove(id);
        persistToFile();
    }

    @Override
    public void createValue(T newInstance) {
        if (newInstance == null || existsById(newInstance.getId())) {
            System.out.println(typeName() + " cannot be null or already existed!");
            return;
        }
        entityMap.put(newInstance.getId(), newInstance);
        persistToFile();
        setNewId(newInstance.getId());
        System.out.println("New " + typeName() + " is added!");
    }

    @Override
    public void updateValue(T instance) {
        if (instance == null || !existsById(instance.getId())) {
            System.out.println(typeName() + " cannot be null or non existed!");
            return;
        }
        entityMap.put(instance.getId(), instance);
        persistToFile();
        System.out.println(typeName() + " is successfully updated!");
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(entityMap.values());
    }

    // -------------------- Utility and Internal Logic --------------------

    /** Generates and returns the next available ID value. */
    public int generateNextId() {
        setNewId(newId + 1);
        return newId;
    }

    /** Updates the last used ID tracker. */
    protected void setNewId(Integer newId) {
        this.newId = newId;
    }

    /** Checks if an entity with the given ID exists in memory. */
    protected boolean existsById(Integer id) {
        return entityMap.containsKey(id);
    }

    /** Persists the entire entity map back into the file system. */
    protected void persistToFile() {
        try (PrintWriter writer = new PrintWriter(Configurations.FILE_ROOT_PATH + repositoryFileName)) {
            for (T entity : entityMap.values()) {
                writer.print(entity.textFormat());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while writing to file: " +
                    Configurations.FILE_ROOT_PATH + repositoryFileName, e);
        }
    }

    /** Sets repository file name and creates a new file if necessary. */
    private void setRepositoryFileName(String repositoryFileName) {
        if (repositoryFileName == null || repositoryFileName.isBlank()) {
            repositoryFileName = initializeFileName();
            createNewRepoFile(repositoryFileName);
        }
        this.repositoryFileName = repositoryFileName;
    }

    /** Creates a new file in the configured repository directory. */
    private void createNewRepoFile(String fileName) {
        File repoFile = new File(Configurations.FILE_ROOT_PATH + fileName);
        try {
            repoFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Loads and deserializes entities from a file into memory. */
    private void loadFromFile() {
        File repository = new File(Configurations.FILE_ROOT_PATH + repositoryFileName);
        if (!repository.exists()) {
            System.out.println("File does not exist: " + repository.getAbsolutePath());
            return;
        }

        try  {
            Scanner reader = new Scanner(Objects.requireNonNull(repository));
            while (reader.hasNextLine()) {
                String[] sourceObjData = reader.nextLine().split(";");
                T newInstance = getObjectFromData(sourceObjData);
                entityMap.put(newInstance.getId(), newInstance);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // -------------------- Abstract Methods --------------------

    /**
     * Specifies the default filename for the repository when none is provided.
     *
     * @return the repository filename
     */
    protected abstract String initializeFileName();

    /**
     * Specifies a human-readable type name for the managed entity,
     * used primarily in log or console messages.
     *
     * @return the entity type name
     */
    protected abstract String typeName();
}
