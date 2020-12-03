package ru.digitalhabbits.homework1.service;

import javax.annotation.Nonnull;
import java.io.File;

import static java.util.Arrays.stream;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {
        // TODO: NotImplemented
        return true;
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
        //new Directory
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);
        if (!resultDir.exists()) {
            if (resultDir.mkdir()) {
                System.out.println("Directory is created");
            } else {
                System.out.println("Failed to create Directory");
            }
        } else {
            stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                    .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
        }
    }
}
