package me.dakdot.game;

import me.dakdot.game.command.CommandManager;
import me.dakdot.game.listeners.PlayerDeathListener;
import me.dakdot.game.listeners.PlayerInteractListener;
import me.dakdot.game.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        ConfigManager.get().init();
        MessageManager.get();
        CommandManager.get().init();
        ArenaManager.get().init();
        GameManager.get().init();

        this.getCommand("game").setExecutor(CommandManager.get());

        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable() {

    }

}
