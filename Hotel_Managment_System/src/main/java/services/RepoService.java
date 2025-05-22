package services;

import config.Configurations;
import func.FileFormatter;
import func.ObjectProvider;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/*
    Това е клас който ще служи като абстракция над взимането на данни и ще съдържа нормалните CRUD операции, защото четенето
    и записването на елементи във файлове е тежка и бавна операция (все пак не използме база данни като mysql, oracle...)
 */
public abstract class RepoService<T extends FileFormatter> implements ObjectProvider<T> {
    private static final String ELEMENT_NOT_FOUND_MESSAGE = "Element with id: %d is not found!";
    protected static final String REGEX_EXPRESSION = ":";
    protected static final int VALUE_POSITION = 1;

    private final Map<Integer, T> entityMap;
    private String repositoryFileName;
    private int newId;

    public RepoService(String repositoryFileName) {
        setRepositoryFileName(repositoryFileName);
        entityMap = new LinkedHashMap<>();
        loadFromFile();
    }

    // От тук започват обикновенните "CRUD" операции, които трябва да може да предоставя един такъв клас като функционалност
    public T findById(int id) {
        return entityMap.get(id);
    }

    public Map<Integer, T> getEntities() {
        return Map.copyOf(entityMap);
    }

    public List<T> findAll() {
        List<T> entities = entityMap.values()
                .stream()
                .toList();

        return List.copyOf(entities);
    }

    public void deleteById(int id) {
        /*
            Метода който запазва информация във файла е много бавна операция! Затова ни трябва тази проверка за да сме
            сигурни, че id-то съзществува и да не бавим програмата излишно
        */
        if (!existsById(id)) {
            System.out.printf((ELEMENT_NOT_FOUND_MESSAGE) + "%n", id);
            return;
        }

        entityMap.remove(id);
        persistToFile();
    }
    // Тук приключва имплементационната секция с обикновенните CRUD операции--------------------------------------------


    public int generateNextId() {
        setNewId(newId + 1);
        return newId;
    }

    protected void setNewId(int newId) {
        this.newId = newId;
    }

    protected Map<Integer, T> getEntityMap() {
        return entityMap;
    }

    protected boolean existsById(int id) {
        return entityMap.containsKey(id);
    }

    protected void persistToFile() {
        // Това бих го сложил в отделен thread, но засега става и така!
        try (PrintWriter writer = new PrintWriter(Configurations.FILE_ROOT_PATH + repositoryFileName)) {
            for (T currRoom : entityMap.values()) {
                writer.print(currRoom.toFileFormat());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while writing to file: " + Configurations.FILE_ROOT_PATH + repositoryFileName, e);
        }
    }

    private void setRepositoryFileName(String repositoryFileName) {
        if (repositoryFileName == null || repositoryFileName.isBlank()) {
            repositoryFileName = initializeFileName();
            createNewRoomFile(repositoryFileName);
        }

        this.repositoryFileName = repositoryFileName;
    }

    private void createNewRoomFile(String fileName) {
        File repoFile = new File(Configurations.FILE_ROOT_PATH + fileName);
        try {
            repoFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFromFile() {
        //InputStream repository = getClass().getClassLoader().getResourceAsStream(repositoryFileName)
        File repository = new File(Configurations.FILE_ROOT_PATH + repositoryFileName);
        if (!repository.exists()) {
            System.out.println("File does not exist: " + repository.getAbsolutePath());
            return;
        }

        try {
            Scanner reader = new Scanner(Objects.requireNonNull(repository));
            while (reader.hasNextLine()) {
                /*
                    Тук в regex си позволяваме да хардкоднем ";" защото приемаме само generic типове, които имплементират
                    FileFormatter. Всички класове, които имплементират FileFormatter ни е гарантирано, че са разделени
                    с ";" между всички отделни части на записа.
                */
                String[] sourceObjData = reader.nextLine().split(";");

                // Тук можем да си позволим този метод да е void защото структурите от данни се предават по референция!
                mapDataFromFileLine(entityMap, sourceObjData);
            }

        } catch (Exception е) {
            // Игнорираме за момента грешката защото в противен случай протграмата ще спре!
            System.out.println(е.getMessage());
        }
    }


    /*
       Абстрактни методи, които наследниците ще трябва да имплементират, защото всеки наследник ще ги извършва по
       специфичен и различен начин.
    */
    public abstract void updateValue(T instance);
    public abstract void createValue(T instance);

    protected abstract void mapDataFromFileLine(Map<Integer, T> entityMap, String[] sourceObjData);
    protected abstract String initializeFileName();
}
