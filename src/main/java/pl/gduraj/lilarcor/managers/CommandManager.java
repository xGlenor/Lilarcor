package pl.gduraj.lilarcor.managers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigType;
import pl.gduraj.lilarcor.utils.Message;
import pl.gduraj.lilarcor.utils.NBTUtil;
import pl.gduraj.lilarcor.utils.TextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandExecutor, TabExecutor {

    private Lilarcor plugin;
    private FileConfiguration config;

    public CommandManager(){
        this.plugin = Lilarcor.getInstance();
        this.plugin.getCommand("lilarcor").setExecutor(this);
        this.plugin.getCommand("lilarcor").setTabCompleter(this);
        this.config = plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("lilarcor.admin")){
            sender.sendMessage(Message.NO_PERMISSION.toString());
            return true;
        }

        if(args.length > 0){

            if(args[0].equalsIgnoreCase("reload")){                                                                 // RELOAD PLUGINU LUB POJEDYNCZYCH MODUŁÓW
                if(args.length == 1) {
                    this.plugin.getConfigManager().reloadFiles();
                    sender.sendMessage(Message.RELOAD_ALL.toString());
                    return true;
                }

                this.plugin.getToolsManager().getTools().get(args[1].toUpperCase()).reloadTool();
                sender.sendMessage(Message.RELOAD_CONFIG.toString().replace("%plik%", args[1].toUpperCase()));
                return true;

            }

            else if(args[0].equalsIgnoreCase("help")){                                                               // INFORMACJE NA TEMAT PLUGINU
                for (String help : this.config.getStringList("Message.commandHelp")){
                    sender.sendMessage(TextUtil.color(help));
                    return true;
                }
            }

            else if(args[0].equalsIgnoreCase("log")){
                plugin.getConfigManager().formatMessage("Gracz " + args[1] + " zakupil dostep do "+args[2], "Kupno");
                return true;
            }

            else if(args[0].equalsIgnoreCase("check")){
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    if(!player.getInventory().getItemInMainHand().getType().isAir()){
                        player.sendMessage(Message.KILL_VALUE.toString().replace("%kills%", NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), "Wartosc").toString()));
                        return true;
                    }else {
                        player.sendMessage(Message.NO_ITEM.toString());
                        return true;
                    }
                }
                sender.sendMessage(Message.ONLY_PLAYER.toString());
                return true;
            }

            else if(args[0].equalsIgnoreCase("get")){
                Player player = Bukkit.getPlayerExact(args[1]);

                if(player == null) {
                    sender.sendMessage(Message.OFFLINE_PLAYER.toString());
                    return true;
                }
                player.getInventory().addItem(this.plugin.getToolsManager().getTools().get(args[2].toUpperCase()).getItem(Integer.parseInt(args[3])));
                return true;
            }

            else if(args[0].equalsIgnoreCase("setvalue")){
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if(itemStack.getType().isAir()) {
                        player.sendMessage(Message.NO_ITEM.toString());
                        return true;
                    }
                    player.getInventory().setItemInMainHand(NBTUtil.addNBT(itemStack, "Wartosc", Integer.parseInt(args[1])));
                    player.sendMessage(Message.SET_VALUE.toString().replace("%kills%", args[1]));
                    return true;
                }
            }


        }else{
            for(String help : this.config.getStringList("Message.commandHelp")) {
                sender.sendMessage(TextUtil.color(help));
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            return Arrays.asList("get", "setValue", "check", "log", "reload");
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("get")){
                List<String> list = new ArrayList<>();
                this.plugin.getServer().getOnlinePlayers().forEach(p -> list.add(p.getName()));
                return list;
            }else if(args[0].equalsIgnoreCase("reload")){
                return Arrays.asList("SWORD", "AXE", "PICKAXE");
            }
        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("get")){
                return Arrays.asList("SWORD", "AXE", "PICKAXE");
            }
        }


        return null;
    }
}
