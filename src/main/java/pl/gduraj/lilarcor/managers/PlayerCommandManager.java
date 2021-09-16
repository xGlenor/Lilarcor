package pl.gduraj.lilarcor.managers;

import com.google.common.collect.Table;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigType;
import pl.gduraj.lilarcor.utils.Message;
import pl.gduraj.lilarcor.utils.XMaterial;

import java.util.HashMap;

public class PlayerCommandManager implements CommandExecutor {

    private Lilarcor plugin;
    private FileConfiguration config;
    private FileConfiguration data;

    public PlayerCommandManager(){
        this.plugin = Lilarcor.getInstance();
        this.plugin.getCommand("lil").setExecutor(this);
        this.config = this.plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("lilarcor.command.player")) {
            sender.sendMessage(Message.NO_PERMISSION.toString());
            return true;
        }

        if(!(sender instanceof Player)){
            sender.sendMessage(Message.NO_PERMISSION.toString());
            return true;
        }

        Player player = (Player) sender;

        return true;

    }

}
