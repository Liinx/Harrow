package me.lynx.harrow.file;

import me.lynx.harrow.exception.ResourceFileNotFoundException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class YAMLFile implements HFile {

    private String name;
    private File file;
    private YamlConfiguration yaml;

    private YAMLFile() {}

    protected YAMLFile(JavaPlugin plugin, String name, boolean copyFromResource) {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        this.name = name;

        file = new File(plugin.getDataFolder(), name + ".yml");
        if (!file.exists()) {
            try {
                if (copyFromResource) {
                    InputStream inputStream = plugin.getResource(name + ".yml");
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