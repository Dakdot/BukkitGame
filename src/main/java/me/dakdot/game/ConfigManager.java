package me.dakdot.game;

public class ConfigManager {

    public ConfigManager() { }

    private static ConfigManager instance; // Singleton stuff
    public static ConfigManager get() {
        if (instance == null) instance = new ConfigManager();
        return instance;
    }

    private ConfigFile file;

    public void init() {
        file = new ConfigFile("config.yml");
    }

    public void reload() { file.reload(); }

    public static String getString(String path) {
        return get().file.get().getString(path);
    }

    public static int getInt(String path) {
        return get().file.get().getInt(path);
    }

    public static boolean getBoolean(String path) { return get().file.get().getBoolean(path); }

}
