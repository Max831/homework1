package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.Arrays.stream;
import static org.slf4j.LoggerFactory.getLogger;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";
    private static final Logger logger = getLogger(FileEngine.class);

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);
        File file = new File(resultDir, pluginName + ".txt");
        try {
            if (file.createNewFile()) { // create the file
                try (FileWriter writer = new FileWriter(file.getAbsolutePath(), false)) {
                    writer.write(text);
                    writer.flush();
                }
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return true;
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
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
