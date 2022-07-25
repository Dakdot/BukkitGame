package me.dakdot.game;

import org.bukkit.command.CommandSender;

public class MessageManager {


    private static MessageManager instance; // Singleton stuff
    public static MessageManager get() {
        if (instance == null) instance = new MessageManager();
        return instance;
    }

    private ConfigFile msgFile;

    public MessageManager() {
        msgFile = new ConfigFile("messages.yml");
    }

    public static void info(CommandSender s, String msg, String... args) {
        if (args.length == 0) s.sendMessage(format(msg));
        else {
            String st = format(msg);
            for (int i = 0; i < args.length; i++) st = st.replaceAll("(%" + i + ")", args[i]);
            s.sendMessage(st);
        }
    }

    public static void error(CommandSender s, String msg) {
        info(s, "&4" + msg); // TODO: Create actual formatting in messages.yml
    }

    public static void warn(CommandSender s, String msg) {
        info(s, "&e" + msg);
    }

    public void reload() { msgFile.reload(); }

    public static String formatColor(String s) {
        return s.replaceAll("&\\b", "\u00A7");
    }

    public static String format(String s) {
        String st = get().msgFile.get().getString(s);
        if (st != null) return formatColor(st);
        return formatColor(s);
    }

    public static String getString(String path) {
        return get().msgFile.get().getString(path);
    }

    public static int getInt(String path) {
        return get().msgFile.get().getInt(path);
    }

}
