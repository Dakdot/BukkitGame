package me.dakdot.game.command;

import me.dakdot.game.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help extends Subcommand {

    public Help() {
        this.name = "help";
        this.description = "Displays either a list of all commands, or a description of a specified command.";
        this.aliases = new String[] { "?" };
        this.arguments = new String[] { "[command]" };
        this.console = true;
    }

    @Override
    public void onRun(CommandSender s, String[] args) {
        if (args.length > 0) {
            Subcommand c = CommandManager.getCommand(args[0]);
            if (c == null) {
                MessageManager.error(s, "The command " + args[0] + " does not exist.");
            } else {
                MessageManager.info(s, "Showing help for " + c.name + ":");
                c.showUsageMessage(s);
                String aliases = "";
                if (c.aliases != null) for (int i = 0; i < c.aliases.length; i++) aliases += c.aliases[i] + " ";
                aliases.trim();
                MessageManager.info(s, "Aliases: " + aliases);
                MessageManager.info(s, c.description);
                return;
            }
        }

        MessageManager.info(s, "Showing help:");
        for (Subcommand c : CommandManager.get().getCommands()) {
            if (!(s instanceof Player) && !c.console) break; // if (sender is console & command is not console)

            String a = " ";
            if (c.arguments != null) for (int i = 0; i < c.arguments.length; i++) a += c.arguments[i] + " ";
            a.trim();
            MessageManager.info(s, "/game " + c.name + a);
        }
    }
}
