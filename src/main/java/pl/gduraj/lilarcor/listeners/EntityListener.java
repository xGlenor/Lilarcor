package pl.gduraj.lilarcor.listeners;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigType;
import pl.gduraj.lilarcor.managers.ToolsManager;
import pl.gduraj.lilarcor.utils.*;

import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {

    private Lilarcor plugin;

    public EntityListener(){
        this.plugin = Lilarcor.getInstance();
    }

    @EventHandler
    public void onEntitykill(EntityDeathEvent event){
        if(event.getEntity().getKiller() != null){
                Player player = event.getEntity().getKiller();

                if(player.getInventory().getItemInMainHand().getType().isAir()) return;

                if(!this.plugin.getToolsManager().checkTool(player.getInventory().getItemInMainHand())) return;

                String type = getToolsManager().getNameTool(player.getInventory().getItemInMainHand());
                String entity = event.getEntity().getType().name().toUpperCase();

            if(getToolsManager().getTools().get(type).getQuests().containsKey(entity) && getToolsManager().getTools().get(type).getQuests().get(entity).getString("type").equals("KILL")){
                ItemStack item = player.getInventory().getItemInMainHand();

                if(getToolsManager().getTools().get(type).getQuests().get(entity).getInt("tool") ==
                        NBTUtil.getIntNBT(item, type.toUpperCase())){

                    player.getInventory().setItemInMainHand(getToolsManager().getTools().get(type).addValue(item, 1)); // addValue
                    player.sendMessage(Message.GOOD_INFO.toString());
                    item = player.getInventory().getItemInMainHand();
                    // LEVEL UP
                    if(NBTUtil.getIntNBT(item, "Wartosc") >= getToolsManager().getTools().get(type).getQuests().get(entity).getInt("value")){
                        player.getInventory().setItemInMainHand(getToolsManager().getTools().get(type).levelUP(item));
                        player.playSound(player.getLocation(), XSound.matchXSound("ENTITY_GENERIC_EXPLODE").get().parseSound(), 10, 1);

                        if(NBTUtil.getIntNBT(item, type.toUpperCase()).equals(1)){ // IF LEVEL FROM TOOL 1 TO TOOL 2
                            player.sendMessage(TextUtil.color(getConfig(ConfigType.SETTINGS).getString("Message.levelUP."+type.toUpperCase())));
                            return;
                        }

                    }

                }

            }

        }

    }

    @EventHandler
    public void onDamege(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();

            if(player.getInventory().getItemInMainHand().getType().isAir()) return;
            if(!this.plugin.getToolsManager().checkTool(player.getInventory().getItemInMainHand())) return;

            String type = getToolsManager().getNameTool(player.getInventory().getItemInMainHand());

            if(type.equals("SWORD") && !getToolsManager().getTools().get(type).getEventStatus()) return;
            if(type.equals("AXE") && !getToolsManager().getTools().get(type).getEventStatus()) return;
            if(type.equals("PICKAXE") && !getToolsManager().getTools().get(type).getEventStatus()) return;

            String entity = event.getEntity().getType().name().toUpperCase();

            double chance = getToolsManager().getTools().get(type).getClickChance();

            if(Util.chance(chance/100)) {
                if(NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), getToolsManager().getNameTool(player.getInventory().getItemInMainHand())).equals(1)) return;
                if(NBTUtil.getBoolean(player.getInventory().getItemInMainHand(), "Powiadomienia")) return;
                if (getConfig(ConfigType.valueOf(type.toUpperCase())).getConfigurationSection("onEvent").contains(entity)) {
                    List<String> messageList = getConfig(ConfigType.valueOf(type.toUpperCase())).getStringList("onEvent." + entity + "." + NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), type.toUpperCase()));
                    player.sendMessage(TextUtil.color(messageList.get(new Random().nextInt(messageList.size()))));
                }else if(event.getEntity() instanceof Animals){
                    List<String> messageList = getConfig(ConfigType.valueOf(type.toUpperCase())).getStringList("onEvent.OTHER_PASSIVE." + NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), type.toUpperCase()));
                    player.sendMessage(TextUtil.color(messageList.get(new Random().nextInt(messageList.size()))));
                }else if(event.getEntity() instanceof Monster){
                    List<String> messageList = getConfig(ConfigType.valueOf(type.toUpperCase())).getStringList("onEvent.OTHER_AGRESSIVE." + NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), type.toUpperCase()));
                    player.sendMessage(TextUtil.color(messageList.get(new Random().nextInt(messageList.size()))));
                }
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
