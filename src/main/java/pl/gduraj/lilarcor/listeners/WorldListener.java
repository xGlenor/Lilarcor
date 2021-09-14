package pl.gduraj.lilarcor.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigType;
import pl.gduraj.lilarcor.managers.ToolsManager;
import pl.gduraj.lilarcor.utils.Message;
import pl.gduraj.lilarcor.utils.NBTUtil;
import pl.gduraj.lilarcor.utils.TextUtil;
import pl.gduraj.lilarcor.utils.Util;

import java.util.List;
import java.util.Random;

public class WorldListener implements Listener {
    private Lilarcor plugin;

    public WorldListener(){
        this.plugin = Lilarcor.getInstance();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(event.isCancelled()) return;

        if(event.getPlayer().getInventory().getItemInMainHand().getType().isAir()) return;

        if(!this.plugin.getToolsManager().checkTool(event.getPlayer().getInventory().getItemInMainHand())) return;

        Player player = event.getPlayer();
        String block = event.getBlock().getType().toString().toUpperCase();
        String type = this.plugin.getToolsManager().getNameTool(player.getInventory().getItemInMainHand());

        if(getToolsManager().getTools().get(type).getQuests().containsKey(block) && getToolsManager().getTools().get(type).getQuests().get(block).getString("type").equals("BREAK")){
            ItemStack item = player.getInventory().getItemInMainHand();

            if(getToolsManager().getTools().get(type).getQuests().get(block).getInt("tool") ==
                    NBTUtil.getIntNBT(item, type.toUpperCase())){

                player.getInventory().setItemInMainHand(getToolsManager().getTools().get(type).addValue(item, 1)); // addValue
                player.sendMessage(Message.GOOD_INFO.toString());
                item = player.getInventory().getItemInMainHand();
                // LEVEL UP
                if(NBTUtil.getIntNBT(item, "Wartosc") >= getToolsManager().getTools().get(type).getQuests().get(block).getInt("value")){
                    player.getInventory().setItemInMainHand(getToolsManager().getTools().get(type).levelUP(item));

                    if(NBTUtil.getIntNBT(item, type.toUpperCase()).equals(1)){ // IF LEVEL FROM TOOL 1 TO TOOL 2
                        player.sendMessage(Message.LEVEL_UP.toString());
                    }

                }

            }

        }


    }

    @EventHandler
    public void onEventBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType().isAir()) return;
        String type = getToolsManager().getNameTool(player.getInventory().getItemInMainHand());
        if(!getToolsManager().checkTool(player.getInventory().getItemInMainHand())) return;


        if(type.equals("SWORD") && !getToolsManager().getTools().get(type).getEventStatus()) return;
        if(type.equals("AXE") && !getToolsManager().getTools().get(type).getEventStatus()) return;
        if(type.equals("PICKAXE") && !getToolsManager().getTools().get(type).getEventStatus()) return;

        if(player.getInventory().getItemInMainHand().getType().isAir()) return;


        String block = event.getBlock().getType().toString().toUpperCase();
        double chance = getToolsManager().getTools().get(type).getClickChance();

        if(NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), getToolsManager().getNameTool(player.getInventory().getItemInMainHand())).equals(1)) return;

        if(Util.chance(chance/100)) {
            if(!getToolsManager().getTypeEvent(player.getInventory().getItemInMainHand()).equals("BREAK")) return;
            if (getConfig(ConfigType.valueOf(type.toUpperCase())).getConfigurationSection("onEvent").contains(block)) {
                List<String> messageList = getConfig(ConfigType.valueOf(type.toUpperCase())).getStringList("onEvent." + block + "." + NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), type.toUpperCase()));
                player.sendMessage(TextUtil.color(messageList.get(new Random().nextInt(messageList.size()))));
            }else if (this.plugin.getCustomMaterials().get(type.toUpperCase()).contains(block)){
                List<String> messageList = getConfig(ConfigType.valueOf(type.toUpperCase())).getStringList("onEvent.Custom1." + NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), type.toUpperCase()));
                player.sendMessage(TextUtil.color(messageList.get(new Random().nextInt(messageList.size()))));
            }else{
                List<String> messageList = getConfig(ConfigType.valueOf(type.toUpperCase())).getStringList("onEvent.Custom2." + NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), type.toUpperCase()));
                player.sendMessage(TextUtil.color(messageList.get(new Random().nextInt(messageList.size()))));
            }
        }

    }


    private FileConfiguration getConfig(ConfigType type){
        return this.plugin.getConfigManager().getFile(type).getConfig();
    }

    private ToolsManager getToolsManager(){
        return this.plugin.getToolsManager();
    }

}
