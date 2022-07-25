package me.dakdot.game.command;

import me.dakdot.game.MessageManager;
import org.bukkit.command.CommandSender;

public abstract class Subcommand {

    public String name;
    public String description;
    public String[] aliases = null;
    public String[] arguments = null;
    public boolean console;

    public abstract void onRun(CommandSender s, String[] args);

    public void showUsageMessage(CommandSender s) {
        String a = " ";
        if (arguments != null) for (int i = 0; i < arguments.length; i++) a += arguments[i] + " ";
        a.trim();
        MessageManager.info(s, "Usage: /game " + name + a);
    }

}
