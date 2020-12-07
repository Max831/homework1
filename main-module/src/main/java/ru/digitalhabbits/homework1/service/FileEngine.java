package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
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
        //if (!resultDir.exists()) {
            try {

                File file = new File(resultDir, pluginName + ".txt"); // put the file inside the folder
                file.createNewFile(); // create the file
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage());
            }
       // }
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
