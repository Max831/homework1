package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        // TODO: NotImplemented
        File dir = new File(PACKAGE_TO_SCAN); //path указывает на директорию
        List<Class<? extends PluginInterface>> lst = new ArrayList<>();
        ru.digitalhabbits.homework1.plugin.PluginInterface
        for ( File file : dir.listFiles() ){
            if ( file.isFile() )
                lst.add(file.;
        }
        return lst;
    }
}
