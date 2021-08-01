package pl.gduraj.lilarcor.managers;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigType;

import pl.gduraj.lilarcor.tools.Tool;
import pl.gduraj.lilarcor.utils.*;

import java.util.HashMap;


public class CommandManagsser implements CommandExecutor {

    private Lilarcor plugin;
    private FileConfiguration config;

    public CommandManagsser(){
        this.plugin = Lilarcor.getInstance();
        this.plugin.getCommand("lilarcor").setExecutor(this);
        this.config = plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("lilarcor.admin")) {
            sender.sendMessage(Message.NO_PERMISSION.toString());
            return true;
        }

        if(args.length > 0) {

            if(args[0].equalsIgnoreCase("reload")) {
                if(args.length == 1) plugin.getConfigManager().reloadFile("all");
                plugin.getToolsManager().reloadTools();
//                plugin.getConfigManager().reloadFile(args[1]);
//                if(args[1].contains("all")) sender.sendMessage(Message.RELOAD_ALL.toString());
//                else sender.sendMessage(Message.RELOAD_CONFIG.toString().replace("%plik%", args[1]));
                return true;
            }


            else if(args[0].equalsIgnoreCase("help")) {
                for (String help : plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig().getStringList("Message.commandHelp")) {
                    sender.sendMessage(TextUtil.color(help));
                }
                return true;
            }


            else if (args[0].equalsIgnoreCase("log")) {
                plugin.getConfigManager().formatMessage("Gracz " + args[1] + " zakupil dostep do "+args[2], "Kupno");
                return true;
            }


            else if(args[0].equalsIgnoreCase("check")) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if(player.getInventory().getItemInMainHand().getType() != Material.AIR){
                        player.sendMessage(Message.KILL_VALUE.toString().replace("%kills%", NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), "Wartosc").toString()));
                        return true;
                    }
                    player.sendMessage(TextUtil.color(config.getString("Message.noItem")));
                    return true;
                }
            }

            else if(args[0].equalsIgnoreCase("get")) {

                Player player = Bukkit.getPlayerExact(args[1]);
                if(player == null){
                    sender.sendMessage(TextUtil.color(config.getString("Message.offlinePlayer")));
                    return true;
                }
                player.getInventory().addItem(new ItemStack(this.plugin.getToolsManager().getTools().get(args[2].toUpperCase()).getItem(Integer.parseInt(args[3]))));
                return true;
            }


            else if(args[0].equalsIgnoreCase("test1")) {
                NBTUtil.infoNBT(((Player) sender).getInventory().getItemInMainHand());
                return true;
            }

            else if(args[0].equalsIgnoreCase("set")) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if(itemStack.getType() == Material.AIR) return true;
                    player.getInventory().setItemInMainHand(NBTUtil.addNBT(
                            player.getInventory().getItemInMainHand(), "Wartosc", Integer.parseInt(args[1])
                    ));
                    player.sendMessage(TextUtil.color(
                            config.getString("Message.setKills").replace("%kills%", args[1])
                    ));
                    return true;

                }
            }

            else if(args[0].equalsIgnoreCase("spawn")){
                Player player = (Player) sender;
                int liczba = Integer.parseInt(args[1]);
                for (int i = 0; i < liczba; i++) {
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(args[2]));
                }
                return true;
            }

        }

        for (String help : plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig().getStringList("Message.commandHelp")) {
            sender.sendMessage(TextUtil.color(help));
        }
        return true;


//        if (sender instanceof Player) {
//
//
//            if(args[0].equalsIgnoreCase("get")) {
//                player.sendMessage("Wartosc: "+ NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), "Wartosc"));
//                player.sendMessage(args[1]+": "+ NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), args[1].toUpperCase()));
//                return true;
//            }else if(args[0].equalsIgnoreCase("info")){
//                NBTItem nbti = new NBTItem(player.getInventory().getItemInMainHand());
//                player.sendMessage(nbti.getCompound().toString());
//                return true;
//            }else if(args[0].equalsIgnoreCase("help")) {
//                for (String help : plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig().getStringList("Message.commandHelp")) {
//                    player.sendMessage(TextUtil.color(help));
//                }
//                return true;
//            }else if(args[0].equalsIgnoreCase("reload")) {
//                plugin.getConfigManager().reloadFiles();
//                player.sendMessage(TextUtil.color(plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig().getString("Message.reloadPlugin")));
//                return true;
//            }
//
//            ItemBuilder builder = ItemBuilder.getItem(plugin.getConfigManager().getFile(ConfigType.valueOf(args[0].toUpperCase())).getConfig().getConfigurationSection("items."+args[1]), player);
//            builder.addNBT(args[0].toUpperCase(), Integer.parseInt(args[1]));
//            builder.addNBT("Wartosc", 0);
//            player.getInventory().addItem(new ItemStack(builder.build()));
//            player.sendMessage("GZ");
//        }
    }

    private ToolsManager getToolsManager(){
        return this.plugin.getToolsManager();
    }

}
