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
import java.util.jar.JarFile;

import static com.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);
    private static List<Class<? extends PluginInterface>> plugins = new ArrayList<>();
    private static final String PLUGIN_EXT = ".jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        File pluginDir = new File(pluginDirName);
        if (!pluginDir.exists()) {
            pluginDir.mkdir();
        }
        File[] files = pluginDir.listFiles((dir, name) -> name.endsWith(PLUGIN_EXT));
        ArrayList<String> classes = new ArrayList<>();

        ArrayList<URL> urls = new ArrayList<>(files.length);

        if (files.length > 0) {
            try {
                for (File file : files) {
                    JarFile jar = new JarFile(file);
                    jar.stream().forEach(jarEntry -> {
                        if (jarEntry.getName().replace("/", ".").startsWith(PACKAGE_TO_SCAN) && jarEntry.getName().endsWith(".class")) {
                            classes.add(jarEntry.getName());
                        }
                    });
                    URL url = file.toURI().toURL();
                    urls.add(url);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        for (var item : urls) {
            URL[] currentItem = urls.toArray(new URL[]{item});
            URLClassLoader urlClassLoader = new URLClassLoader(currentItem);
            classes.forEach(className -> {
                try {
                    Class loadClass = urlClassLoader.loadClass(className.replaceAll("/", ".").replace(".class", "")); //transforming to binary name
                    Class[] interfaces = loadClass.getInterfaces();
                    for (Class plugin : interfaces) {
                        if (plugin.equals(PluginInterface.class)) {
                            plugins.add(loadClass);
                            break;
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage());
                }
            });
        }
        return plugins;
    }
}
