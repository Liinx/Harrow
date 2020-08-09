package me.lynx.iktinos.file;

import me.lynx.iktinos.file.exception.ResourceFileNotFoundException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class YAMLFile implements me.lynx.iktinos.file.File {

    private String name;
    private File file;
    private YamlConfiguration yaml;

    private YAMLFile() {}

    YAMLFile(JavaPlugin plugin, String name, boolean copyFromResource) {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        this.name = name;

        file = new File(plugin.getDataFolder(), name + ".yaml");
        if (!file.exists()) {
            try {
                if (copyFromResource) {
                    InputStream inputStream = plugin.getResource(name + ".yaml");
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
        yaml = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public File getFile() {
        return file;
    }

    public YamlConfiguration getYaml() {
        return yaml;
    }

    public void reload() {
        yaml = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}