package pl.gduraj.lilarcor.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigType;
import pl.gduraj.lilarcor.managers.ToolsManager;
import pl.gduraj.lilarcor.utils.NBTUtil;
import pl.gduraj.lilarcor.utils.TextUtil;
import pl.gduraj.lilarcor.utils.Util;
import pl.gduraj.lilarcor.utils.XSound;

import java.util.Random;

public class PlayerListener implements Listener {

    private Lilarcor plugin;

    public PlayerListener(){
        this.plugin = Lilarcor.getInstance();
    }

    @EventHandler                                                                                                                               // BLOKOWANIE UTRATY GŁODU
    public void onFeedLevelChange(FoodLevelChangeEvent event){

        if(event.isCancelled()) return;

        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(player.getInventory().getItemInMainHand().getType().isAir()) return;

            if(NBTUtil.getIntNBT(player.getInventory().getItemInMainHand(), "PICKAXE").equals(6)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand().getType().isAir()) return;
        if(!this.plugin.getToolsManager().checkTool(event.getPlayer().getInventory().getItemInMainHand())) return;
        if(NBTUtil.getIntNBT(event.getItem(), getToolsManager().getNameTool(event.getItem())).equals(1)) return;


        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        String type = getToolsManager().getNameTool(item).toUpperCase();

        if(type.equals("SWORD") && !getToolsManager().getTools().get(type).getClickStatus()) return;
        if(type.equals("AXE") && !getToolsManager().getTools().get(type).getClickStatus()) return;
        if(type.equals("PICKAXE") && !getToolsManager().getTools().get(type).getClickStatus()) return;


        if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            double chance = getToolsManager().getTools().get(type).getClickChance();
            if(Util.chance(chance/100)) {
                int level = NBTUtil.getIntNBT(item, type);
                String random = getConfig(type).getStringList("onClick."+level+".message").get(new Random().nextInt(getConfig(type).getStringList("onClick."+level+".message").size()));
                player.sendMessage(TextUtil.color(random));
                player.playSound(player.getLocation(), XSound.matchXSound(getConfig(type).getString("onClick."+level+".sound")).get().parseSound(), 10, 1);

            }

        }


    }

//    @EventHandler
//    public void onLogin(PlayerJoinEvent event){
//        if(getConfig("DATA").getStringList("axe").contains(event.getPlayer().getName()))
//            getPlayersData().put(event.getPlayer().getName(), "AXE");
//        if(getConfig("DATA").getStringList("sword").contains(event.getPlayer().getName()))
//            getPlayersData().put( "SWORD", event.getPlayer().getName());
//        if(getConfig("DATA").getStringList("pickaxe").contains(event.getPlayer().getName()))
//            getPlayersData().put(event.getPlayer().getName(), "PICKAXE");
//    }



    private ToolsManager getToolsManager(){
        return this.plugin.getToolsManager();
    }

    private FileConfiguration getConfig(String type){
        return this.plugin.getConfigManager().getFile(ConfigType.valueOf(type)).getConfig();
    }


//    public HashMap<String, String> getPlayersData(){
//        return this.plugin.getPlayersData();
//    }

}
