package pl.gduraj.lilarcor.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.gduraj.lilarcor.Lilarcor;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {
    private final Lilarcor plugin;
    private final String name;
    private final File file;
    private FileConfiguration fileConfiguration;

    public ConfigHandler(Lilarcor plugin, String name) {
        this.plugin = plugin;
        this.name = name+ ".yml";
        this.file = new File(plugin.getDataFolder(), this.name);
        this.fileConfiguration = new YamlConfiguration();
    }

    public void saveDefaultConfig(){
        if(!file.exists()){
            plugin.saveResource(name, false);
        }

        try {
            fileConfiguration.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
            plugin.getLogger().severe("Problem z załadowaniem pliku: "+ name);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public void save() {
        if(fileConfiguration == null || file == null) return;

        try {
            getConfig().save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

}
