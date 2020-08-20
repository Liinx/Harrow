package me.lynx.harrow.file;

import me.lynx.harrow.util.exception.ResourceFileNotFoundException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class TXTFile implements me.lynx.harrow.file.File {

    private String name;
    private File file;

    private TXTFile() {}

    protected TXTFile(JavaPlugin plugin, String name, boolean copyFromResource) {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        this.name = name;

        file = new File(plugin.getDataFolder(), name + ".txt");
        if (!file.exists()) {
            try {
                if (copyFromResource) {
                    InputStream inputStream = plugin.getResource(name + ".txt");
                    if (inputStream == null) throw new ResourceFileNotFoundException("Could not find " + name
                            + ".yaml in the resources directory!");

                    Files.copy(inputStream, file.getAbsoluteFile().toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException | ResourceFileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public File getFile() {
        return file;
    }

}