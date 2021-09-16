package pl.gduraj.lilarcor.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {

    public static ItemStack addNBT(ItemStack itemStack, final String value, final Integer amount) {
        final NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger(value, amount);
        itemStack = nbtItem.getItem();
        return itemStack;
    }

    public static Integer getIntNBT(final ItemStack itemStack, final String value) {
        final NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getInteger(value);
    }

    public static boolean getNBT(final ItemStack itemStack, final String value){
        final NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getBoolean(value);
    }

    public static ItemStack setBoolean(ItemStack itemStack, String name, Boolean value){
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean(name, value);
        return nbtItem.getItem();
    }

    public static boolean getBoolean(ItemStack itemStack, String name){
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getBoolean(name);
    }


    public static boolean hasNBTtag(final ItemStack itemStack, final String value){
        final NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey(value);
    }

    public static void infoNBT(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        System.out.println(nbtItem.getCompound());
    }

}
