package me.lynx.harrow.plugin;

import me.lynx.harrow.HarrowLogger;
import me.lynx.harrow.file.FileFactory;

public class FileService extends FileFactory {

    /**
     * Creates file factory and binds
     * it to the provided plugin.
     *
     * @param plugin Plugin class
     */
    protected FileService(AbstractHarrowPlugin plugin) {
        super(plugin);
        HarrowLogger.info("File Service has been initialized.", plugin.getName());
    }

}