package me.dakdot.game;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class ConfigFile {

    private FileConfiguration fileConfig = null;
    private File file = null;
    private String filename;

    public ConfigFile(String filename) {
        this.filename = filename;
        this.file = new File(Main.plugin.getDataFolder(), filename);
        reload();
    }

    public void reload() {
        fileConfig = YamlConfiguration.loadConfiguration(file);

        Reader defConfigStream = new InputStreamReader(Main.class.getResourceAsStream("/" + filename));
        if (defConfigStream == null) return;

        if (!file.exists()) copyDefaultFile();

        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);

        fileConfig.setDefaults(defConfig);
    }

    public void save() {
        if (fileConfig == null || file == null) return;
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            Main.plugin.getLogger().log(Level.SEVERE, "Could not save configuration file " + filename, e);
        }
    }

    public FileConfiguration get() {
        return fileConfig;
    }

    private void copyDefaultFile() {
        InputStream instream = null;
        OutputStream outstream = null;

        try {
            Main.plugin.getDataFolder().mkdirs();

            instream = Main.class.getResourceAsStream("/" + filename);
            outstream = new FileOutputStream(file);

            int readBytes;
            byte[] buffer = new byte[4096];
            while ((readBytes = instream.read(buffer)) > 0) outstream.write(buffer, 0, readBytes);
        } catch (IOException e) {
            Main.plugin.getLogger().log(Level.SEVERE, "Could not save default configuration file " + filename, e);
        }
    }

}
