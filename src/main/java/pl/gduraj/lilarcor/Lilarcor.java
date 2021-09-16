package pl.gduraj.lilarcor;

import org.bukkit.plugin.java.JavaPlugin;
import pl.gduraj.lilarcor.listeners.EntityListener;
import pl.gduraj.lilarcor.listeners.PlayerListener;
import pl.gduraj.lilarcor.listeners.WorldListener;
import pl.gduraj.lilarcor.managers.CommandManager;
import pl.gduraj.lilarcor.managers.ConfigManager;
import pl.gduraj.lilarcor.managers.ToolsManager;

import java.util.HashMap;
import java.util.List;

public final class Lilarcor extends JavaPlugin {

    private static Lilarcor instance;

    //private HashMap<String, String> playersData;
    private HashMap<String, List<String>> customMaterials;
    private CommandManager commandManager;
    private ConfigManager configManager;
    private ToolsManager toolsManager;



    public static Lilarcor getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        customMaterials = new HashMap<>();
        //this.playersData = new HashMap<>();
        configManager = new ConfigManager();
        configManager.loadFiles(this);

        commandManager = new CommandManager();
        toolsManager = new ToolsManager();

        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public CommandManager getCommandManager() {
        return commandManager;
    }

    public HashMap<String, List<String>> getCustomMaterials() {
        return customMaterials;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ToolsManager getToolsManager() {
        return toolsManager;
    }


    //public HashMap<String, String> getPlayersData() {
    //    return playersData;
    //}
}
