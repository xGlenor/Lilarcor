package pl.gduraj.lilarcor.utils;

import org.bukkit.ChatColor;

public class TextUtil {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String joinString(int index, String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = index; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        return builder.toString();
    }

}
