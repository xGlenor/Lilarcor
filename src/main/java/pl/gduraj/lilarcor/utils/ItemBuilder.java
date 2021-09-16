package pl.gduraj.lilarcor.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack ITEM;

    public ItemBuilder(ItemStack item) {
        this.ITEM = item;
    }

    public static ItemBuilder getItem(ConfigurationSection section) {

        ItemStack item = XMaterial.matchXMaterial(section.getString("material").toUpperCase()).get().parseItem();
        ItemBuilder builder = new ItemBuilder(item);

        if (section.contains("name")) {
            builder.setName(section.getString("name"));
        }

        if (section.contains("unbreakable")) {
            if (section.getBoolean("unbreakable")) {
                builder.withUnbreakable();
            }
        }

        if (section.contains("lore")) {
            builder.withLore(section.getStringList("lore"));
        }

        if (section.contains("enchants")) {
            for (String s : section.getStringList("enchants")) {
                String[] args = s.split(":");
                builder.withEnchantment(Enchantment.getByName(args[0].toUpperCase()), Integer.parseInt(args[1]));
            }
        }

        return builder;
    }

    public ItemStack build() {
        return ITEM;
    }

    public ItemBuilder setName(String name) {
        final ItemMeta meta = ITEM.getItemMeta();
        meta.setDisplayName(TextUtil.color(name));
        ITEM.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withEnchantment(Enchantment enchantment, final int level) {
        ITEM.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder withEnchantment(Enchantment enchantment) {
        ITEM.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder withUnbreakable() {
        final ItemMeta meta = ITEM.getItemMeta();
        meta.setUnbreakable(true);
        ITEM.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withLore(List<String> lore, Player player) {
        final ItemMeta meta = ITEM.getItemMeta();
        List<String> coloredLore = new ArrayList<String>();
        for (String s : lore) {
            coloredLore.add(TextUtil.color(s));
        }
        meta.setLore(coloredLore);
        ITEM.setItemMeta(meta);
        return this;
    }

    public ItemBuilder withLore(List<String> lore) {
        final ItemMeta meta = ITEM.getItemMeta();
        List<String> coloredLore = new ArrayList<String>();
        for (String s : lore) {
            coloredLore.add(TextUtil.color(s));
        }
        meta.setLore(coloredLore);
        ITEM.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addNBT(String type, Integer value) {
        ITEM = NBTUtil.addNBT(ITEM, type, value);
        return this;
    }

    public ItemBuilder addNBTBoolean(String key, Boolean value){
        ITEM = NBTUtil.setBoolean(ITEM, key, value);
        return this;
    }

}
