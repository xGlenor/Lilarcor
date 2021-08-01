package pl.gduraj.lilarcor;

import com.samjakob.spigui.SpiGUI;
import org.bukkit.plugin.java.JavaPlugin;
import pl.gduraj.lilarcor.liteners.EntityListener;
import pl.gduraj.lilarcor.liteners.PlayerListener;
import pl.gduraj.lilarcor.liteners.WorldListener;
import pl.gduraj.lilarcor.managers.CommandManager;
import pl.gduraj.lilarcor.managers.ConfigManager;
import pl.gduraj.lilarcor.managers.PlayerCommandManager;
import pl.gduraj.lilarcor.managers.ToolsManager;

import java.util.HashMap;

public final class Lilarcor extends JavaPlugin {

    public static Lilarcor instance;

    //private HashMap<String, String> playersData;
    private SpiGUI spiGUI;
    private CommandManager commandManager;
    private ConfigManager configManager;
    private ToolsManager toolsManager;
    private PlayerCommandManager playerCommandManager;



    public static Lilarcor getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        spiGUI = new SpiGUI(this);
        //this.playersData = new HashMap<>();
        configManager = new ConfigManager();
        configManager.loadFiles(this);

        commandManager = new CommandManager();
        playerCommandManager = new PlayerCommandManager();
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

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ToolsManager getToolsManager() {
        return toolsManager;
    }

    public SpiGUI getSpiGUI() {
        return spiGUI;
    }

    //public HashMap<String, String> getPlayersData() {
    //    return playersData;
    //}
}
