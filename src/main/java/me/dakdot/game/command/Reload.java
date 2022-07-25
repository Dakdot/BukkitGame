package me.dakdot.game.command;

import me.dakdot.game.ArenaManager;
import me.dakdot.game.ConfigManager;
import me.dakdot.game.MessageManager;
import org.bukkit.command.CommandSender;

public class Reload extends Subcommand {

    public Reload() {
        this.name = "reload";
        this.description = "Reloads all configuration files, including arena files, config, and messages.";
        this.console = true;
        this.aliases = new String[] { "r" };
    }

    @Override
    public void onRun(CommandSender s, String[] args) {
        long start = System.nanoTime();
        MessageManager.info(s, "Reloading configuration files...");

        ConfigManager.get().reload();
        MessageManager.get().reload();
        ArenaManager.get().reload();

        long end = System.nanoTime();
        MessageManager.info(s, "Reload completed. Took " + (end - start) * 10E-6 + " ms.");
    }
}
