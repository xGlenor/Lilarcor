package pl.gduraj.lilarcor.managers;

import com.google.common.collect.Table;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
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
    private SpiGUI spiGUI;

    public PlayerCommandManager(){
        this.plugin = Lilarcor.getInstance();
        this.plugin.getCommand("powiadomienia").setExecutor(this);
        this.config = this.plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig();
        this.data = this.plugin.getConfigManager().getFile(ConfigType.DATA).getConfig();
        this.spiGUI = plugin.getSpiGUI();
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
        player.openInventory(openInv(player));

        return true;

    }

    private Inventory openInv(Player player){
        SGMenu menu = this.spiGUI.create(config.getString("GUI.name"), 3);
        menu.setAutomaticPaginationEnabled(false);

        menu.setButton(11, new SGButton(new ItemBuilder(XMaterial.matchXMaterial("DIAMOND_SWORD").get().parseItem())
                .name(this.config.getString("GUI.sword.name"))
                .lore(this.config.getStringList("GUI.sword.lore"))
                .build()));

        menu.setButton(13, new SGButton(new ItemBuilder(XMaterial.matchXMaterial("DIAMOND_AXE").get().parseItem())
                .name(this.config.getString("GUI.axe.name"))
                .lore(this.config.getStringList("GUI.axe.lore"))
                .build()));

        menu.setButton(15, new SGButton(new ItemBuilder(XMaterial.matchXMaterial("DIAMOND_PICKAXE").get().parseItem())
                .name(this.config.getString("GUI.pickaxe.name"))
                .lore(this.config.getStringList("GUI.pickaxe.lore"))
                .build()));

        return menu.getInventory();
    }

}
