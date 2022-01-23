package pl.gduraj.lilarcor.managers;

import com.google.common.collect.Table;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigType;
import pl.gduraj.lilarcor.utils.Message;
import pl.gduraj.lilarcor.utils.NBTUtil;
import pl.gduraj.lilarcor.utils.TextUtil;


public class PlayerCommandManager implements CommandExecutor {

    private Lilarcor plugin;
    private FileConfiguration config;
    private FileConfiguration data;

    public PlayerCommandManager(){
        this.plugin = Lilarcor.getInstance();
        this.plugin.getCommand("gadula").setExecutor(this);
        this.config = this.plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("lilarcor.command.player")) {
            sender.sendMessage(Message.NO_PERMISSION.toString());
            return true;
        }

        if(args.length > 0){

            if(!sender.hasPermission("lilarcor.command.player.other")){
                sender.sendMessage(Message.NO_PERMISSION.toString());
                return true;
            }

            Player player1 = Bukkit.getPlayer(args[0]);

            if(player1 == null){
                sender.sendMessage(Message.OFFLINE_PLAYER.toString());
                return true;
            }

            if(player1.getInventory().getItemInMainHand().getType().isAir()){
                sender.sendMessage(Message.NO_ITEM.toString());
                return true;
            }

            if(this.plugin.getToolsManager().checkTool(player1.getInventory().getItemInMainHand())){
                if(NBTUtil.getBoolean(player1.getInventory().getItemInMainHand(), "Powiadomienia"))
                    sender.sendMessage(TextUtil.color("&e&lLilarcor &f»Powiadomienia dla gracza &a"+ args[0] + " &fzostały wyłączone!"));
                else
                    sender.sendMessage(TextUtil.color("&e&lLilarcor &f»Powiadomienia dla gracza &a"+ args[0] + " &fzostały włączone!"));
                player1.getInventory().setItemInMainHand(changeStatus(player1));
                return true;
            }
        }

        if(sender instanceof Player ) {
            Player player = (Player) sender;

            if(player.getInventory().getItemInMainHand().getType().isAir()){
                player.sendMessage(Message.NO_ITEM.toString());
                return true;
            }

            if(this.plugin.getToolsManager().checkTool(player.getInventory().getItemInMainHand())){
                player.getInventory().setItemInMainHand(changeStatus(player));
                return true;
            }

        }
        return true;

    }


    private ItemStack changeStatus(Player player){
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(NBTUtil.getBoolean(player.getInventory().getItemInMainHand(), "Powiadomienia")){
            player.sendMessage(Message.MESSAGE_STATUS_ON.toString());
            return NBTUtil.setBoolean(itemStack, "Powiadomienia", false);
        }else{
            player.sendMessage(Message.MESSAGE_STATUS_OFF.toString());
            return NBTUtil.setBoolean(itemStack, "Powiadomienia", true);
        }
    }
}
