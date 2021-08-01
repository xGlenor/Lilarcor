package pl.gduraj.lilarcor.tools;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;
import pl.gduraj.lilarcor.config.ConfigType;
import pl.gduraj.lilarcor.utils.ItemBuilder;
import pl.gduraj.lilarcor.utils.NBTUtil;

import java.util.HashMap;

public class Sword extends Tool{

    private HashMap<String, ConfigurationSection> quests;
    private FileConfiguration config;

    public Sword() {
        super("SWORD");
        this.plugin = Lilarcor.getInstance();
        this.quests = new HashMap<>();
        this.config = plugin.getConfigManager().getFile(ConfigType.SWORD).getConfig();
        loadQuests();
    }

    @Override
    public void loadQuests(){
        ConfigurationSection cs = this.config.getConfigurationSection("numbersQuest");


        for(String nameQuest : cs.getKeys(false)){
            quests.put(nameQuest, this.config.getConfigurationSection("numbersQuest."+nameQuest));
        }

    }

    @Override
    public void reloadTool() {
        this.config = this.plugin.getConfigManager().getFile(ConfigType.SWORD).getConfig();
        this.quests.clear();
        loadQuests();
    }

    @Override
    public ItemStack getItem(int level) {
        ItemBuilder builder = ItemBuilder.getItem(this.config.getConfigurationSection("items."+level));
        builder.addNBT("SWORD", level);
        builder.addNBT("Wartosc", 0);
        return builder.build();
    }

    @Override
    public ItemStack levelUP(ItemStack item){
        int level = NBTUtil.getIntNBT(item, "SWORD")+1;
        return ItemBuilder.getItem(this.config.getConfigurationSection("items."+level)).addNBT("SWORD", level).addNBT("Wartosc", 0).build();
    }

    @Override
    public ItemStack addValue(ItemStack itemStack, int amount) {
        int amountItem = NBTUtil.getIntNBT(itemStack, "Wartosc");
        amountItem += amount;
        return NBTUtil.addNBT(itemStack, "Wartosc", amountItem);
    }

    @Override
    public HashMap<String, ConfigurationSection> getQuests() {
        return quests;
    }

    @Override
    public boolean getClickStatus() {
        return this.config.getBoolean("configurations.clickMessage");
    }

    @Override
    public boolean getEventStatus() {
        return this.config.getBoolean("configurations.eventMessage");
    }

    @Override
    public double getClickChance(){
        return this.config.getDouble("configurations.clickChance");
    }

    @Override
    public double getEventChance(){
        return this.config.getDouble("configurations.eventChance");
    }
}