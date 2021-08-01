package pl.gduraj.lilarcor.tools;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import pl.gduraj.lilarcor.Lilarcor;

import java.util.HashMap;

public abstract class Tool {

    protected final String ToolName;
    protected Lilarcor plugin;

    public Tool(String ToolName) {
        this.ToolName = ToolName;
        this.plugin = Lilarcor.getInstance();
    }

    public abstract void loadQuests();

    public abstract void reloadTool();

    public abstract ItemStack getItem(int level);

    public abstract ItemStack levelUP(ItemStack item);

    public abstract ItemStack addValue(ItemStack itemStack, int amount);

    public abstract HashMap<String, ConfigurationSection> getQuests();

    public abstract boolean getEventStatus();

    public abstract boolean getClickStatus();

    public abstract double getClickChance();

    public abstract double getEventChance();

}
