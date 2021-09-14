package pl.gduraj.lilarcor.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigHandler;
import pl.gduraj.lilarcor.config.ConfigType;
import pl.gduraj.lilarcor.utils.Message;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private Lilarcor plugin;
    private Map<ConfigType, ConfigHandler> configurations;
    private File logsFile;

    public ConfigManager() {
        this.plugin = Lilarcor.getInstance();
        configurations = new HashMap<>();
    }

    public void loadFiles(Lilarcor plugin){
        configurations.put(ConfigType.SETTINGS, new ConfigHandler(plugin, "config"));
        configurations.put(ConfigType.SWORD, new ConfigHandler(plugin, "sword"));
        configurations.put(ConfigType.AXE, new ConfigHandler(plugin, "axe"));
        configurations.put(ConfigType.PICKAXE, new ConfigHandler(plugin, "pickaxe"));
//        configurations.put(ConfigType.DATA, new ConfigHandler(plugin, "data"));
        logsFile = new File(plugin.getDataFolder(), "/logs.txt");
        setupLogs();


        Message.setConfiguration(getFile(ConfigType.SETTINGS).getConfig());
        configurations.values().forEach(ConfigHandler::saveDefaultConfig);

    }

    public ConfigHandler getFile(ConfigType type) {
        return configurations.get(type);
    }

    public void reloadFiles() {
        configurations.values().forEach(ConfigHandler::reload);
    }

    public void reloadFile(String type){
        switch (type.toUpperCase()) {
            case "CONFIG":
                configurations.get(ConfigType.SETTINGS).reload();
                break;
            case "AXE":
                configurations.get(ConfigType.AXE).reload();
                break;
            case "PICKAXE":
                configurations.get(ConfigType.PICKAXE).reload();
                break;
            case "SWORD":
                configurations.get(ConfigType.SWORD).reload();
                break;
//            case "DATA":
//                configurations.get(ConfigType.DATA).reload();
            default:
                reloadFiles();
                break;
        }
    }

    public void saveFiles(ConfigType type) {
        getFile(type).save();
    }

    public void addMessageLogs(final String message){
        try{
            final BufferedWriter bw = new BufferedWriter(new FileWriter(logsFile, true));
            bw.append(message);
            bw.close();
        }catch (final IOException e){
            e.printStackTrace();
        }
    }

    public void formatMessage(final String msg, final String name){
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final String date = dateFormat.format(new Date());
        final String finalMessage = date + " - "+ name + ": " + msg + "\n";
        addMessageLogs(finalMessage);
    }

    public FileConfiguration getFileConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public void setupLogs() {

        if(!logsFile.exists()){
            try {
                logsFile.createNewFile();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

    }

}
