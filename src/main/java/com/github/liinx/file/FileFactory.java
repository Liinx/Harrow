package com.github.liinx.file;

import com.github.liinx.HarrowPlugin;

public class FileFactory {

    private HarrowPlugin plugin;

    /**
     * Creates file factory and binds
     * it to the provided plugin.
     * @param plugin Plugin class
     */
    public FileFactory(HarrowPlugin plugin) {
        this.plugin = plugin;
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
    public File newFile(FileType type, String name, boolean copyFromResource) {
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