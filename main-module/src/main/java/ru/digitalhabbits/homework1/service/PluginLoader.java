package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;

import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = ".jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";
    static List<Class<? extends PluginInterface>> plugins = new ArrayList<>();

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        var currentDir = System.getProperty("user.dir") + "\\+" + pluginDirName + "\\";
        var pluginDir = new File(currentDir);
        getClassesNames(Objects.requireNonNull(pluginDir.listFiles()));

        return plugins;
    }


    private void getClassesNames(File[] files) {
        ArrayList<String> classes = new ArrayList<>();
        ArrayList<URL> urls = new ArrayList<>(files.length);
        for (File file : files) {
            try {
                JarFile jar = new JarFile(file);
                jar.stream().forEach(jarEntry -> {
                    if (jarEntry.getName().endsWith(".class")) {
                        classes.add(jarEntry.getName());
                    }
                });
                URL url = file.toURI().toURL();
                urls.add(url);
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage());
            }
        }
        URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
        classes.forEach(className -> {
            try {
                Class cls = urlClassLoader.loadClass(className.replaceAll("/", ".").replace(".class", ""));
                Class[] interfaces = cls.getInterfaces();
                for (Class intface : interfaces) {
                    if (intface.equals(PluginInterface.class)) {
                        plugins.add(cls);
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                e.printStackTrace();
            }
        });
    }
}
