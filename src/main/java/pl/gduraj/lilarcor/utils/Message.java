package pl.gduraj.lilarcor.utils;

import org.bukkit.configuration.file.FileConfiguration;

public enum Message {

    NO_PERMISSION("noPermission"),
    NO_PERMISSION_COMMAND("noPermissionArg"),
    RELOAD_CONFIG("reloadConfig"),
    RELOAD_ALL("reloadAll"),
    DROP_ITEM("dropItem"),
    ADD_ITEM("addItem"),
    NO_ITEM("noItem"),
    MESSAGE_STATUS_ON("gadulaON"),
    MESSAGE_STATUS_OFF("gadulaOFF"),
    SET_VALUE("setValueLilarcor"),
    KILL_VALUE("killValueLilarcor"),
    OFFLINE_PLAYER("offlinePlayer"),
    ONLY_PLAYER("onlyPlayer"),
    GOOD_INFO("goodInfo"),

    TEST("");

    private static FileConfiguration config;
    private String path;

    Message(String path) {
        this.path = path;
    }

    public static void setConfiguration(FileConfiguration configStatic) { config = configStatic;}

    @Override
    public String toString() {
        String message = config.getString("Message." + this.path);
        return TextUtil.color(message);
    }


}
