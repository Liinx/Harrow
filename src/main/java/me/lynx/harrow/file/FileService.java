package me.lynx.harrow.file;

import me.lynx.harrow.HarrowPlugin;
import me.lynx.harrow.util.HarrowLogger;

public final class FileService {

    private HarrowPlugin plugin;

    /**
     * Creates file factory and binds
     * it to the provided plugin.
     * @param plugin Plugin class
     */
    public FileService(HarrowPlugin plugin) {
        this.plugin = plugin;
        HarrowLogger.info("File Service has been initialized.", plugin.getName());
    }

    /**
     * Creates a new file in the plugin directory(by default) and copies contents of the
     * same file from the resource folder within jar file. If the file with the said name already
     * exists creation and coping will be skipped. Returns null if file type does not exist.
     * @param type the type of file
     * @param name name of the file
     * @param copyFromResource inherit contents from the resource file with same name
     * @return new instance
     */
    public HFile newFile(FileType type, String name, boolean copyFromResource) {
        if (type == FileType.YAML) {
            return new YAMLFile(plugin, name, copyFromResource);
        } else if (type == FileType.TXT) {
            return new TXTFile(plugin, name, copyFromResource);
        }

        return null;
    }

    /**
     * Gets the plugin its bound to.
     * @return the plugin
     */
    public HarrowPlugin getPlugin() {
        return plugin;
    }

}