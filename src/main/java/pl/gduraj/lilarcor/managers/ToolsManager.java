package pl.gduraj.lilarcor.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigHandler;
import pl.gduraj.lilarcor.config.ConfigType;
import pl.gduraj.lilarcor.tools.Axe;
import pl.gduraj.lilarcor.tools.Pickaxe;
import pl.gduraj.lilarcor.tools.Sword;
import pl.gduraj.lilarcor.tools.Tool;
import pl.gduraj.lilarcor.utils.NBTUtil;

import java.util.HashMap;
import java.util.List;


public class ToolsManager {

    private Lilarcor plugin;
    private HashMap<String, Tool> tools;

    public ToolsManager(){
        this.plugin = Lilarcor.getInstance();
        this.tools = new HashMap<>();
        loadTools();
        loadCustomMaterials();
    }

    private void loadTools() {
        tools.put("AXE", new Axe());
        tools.put("PICKAXE", new Pickaxe());
        tools.put("SWORD" , new Sword());
    }

    private void loadCustomMaterials(){
        FileConfiguration config = this.plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig();
        HashMap<String, List<String>> cmats = this.plugin.getCustomMaterials();

        cmats.put("AXE", config.getStringList("CustomList.AXE"));
        cmats.put("PICKAXE", config.getStringList("CustomList.PICKAXE"));

    }

    public void reloadTools(){
        tools.forEach((k, v) -> v.reloadTool());
    }

    public boolean checkTool(ItemStack item){
        if(NBTUtil.getNBT(item, "AXE"))
            return true;
        else if (NBTUtil.getNBT(item, "Lilarcor"))
            return true;
        else if(NBTUtil.getNBT(item, "SWORD"))
            return true;
        else return NBTUtil.getNBT(item, "PICKAXE");
    }

    public String getNameTool(ItemStack item){
        if(NBTUtil.hasNBTtag(item, "AXE"))
            return "AXE";
        else if(NBTUtil.hasNBTtag(item, "SWORD"))
            return "SWORD";
        else if (NBTUtil.hasNBTtag(item, "PICKAXE"))
            return "PICKAXE";
        else
            return null;
    }

    public String getTypeEvent(ItemStack item) {
        String type = getNameTool(item).toUpperCase();
        int level = NBTUtil.getIntNBT(item, type);
        return this.plugin.getConfigManager().getFile(ConfigType.valueOf(type)).getConfig().getString("typeTool."+level);
    }

    public HashMap<String, Tool> getTools() {
        return tools;
    }
}
