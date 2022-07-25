package me.dakdot.game.command.editor;

import me.dakdot.game.MessageManager;
import me.dakdot.game.command.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditorHelp extends Subcommand  {

    public EditorHelp() {
        this.name = "help";
        this.description = "Shows arena editor help screen.";
        this.aliases = new String[] { "?" };
        this.arguments = new String[] { "[command]" };
    }

    @Override
    public void onRun(CommandSender s, String[] args) {
        if (args.length > 0) {
            Subcommand c = Editor.getCommand(args[0]);
            if (c == null) {
                MessageManager.error(s, "The editor command " + args[0] + " does not exist.");
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
        for (Subcommand c : Editor.getCommands()) {
            if (!(s instanceof Player) && !c.console) break; // if (sender is console & command is not console)

            String a = " ";
            if (c.arguments != null) for (int i = 0; i < c.arguments.length; i++) a += c.arguments[i] + " ";
            a.trim();
            MessageManager.info(s, "/game editor " + c.name + a);
        }
    }
}
