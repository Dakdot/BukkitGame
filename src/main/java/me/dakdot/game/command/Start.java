package me.dakdot.game.command;

import me.dakdot.game.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.CommandSender;

public class Start extends Subcommand {

    public Start() {
        this.name = "start";
        this.description = "DEBUG COMMAND";
        this.console = true;
    }

    @Override
    public void onRun(CommandSender s, String[] args) {
        Arena a;
        if (NumberUtils.isNumber(args[0])) {
            a = ArenaManager.getArena(NumberUtils.createInteger(args[0]));
            if (a == null) {
                MessageManager.error(s, "An arena with ID " + args[0] + " does not exist.");
                return;
            }
        } else {
            MessageManager.error(s, "Please provide an arena ID.");
            return;
        }

        Game g = GameManager.get().getGame(a);
        if (g == null) {
            MessageManager.error(s, "Cannot start game in arena " + a.getId() + ": no game exists.");
            return;
        }

        g.start();
        MessageManager.info(s, "Started game in arena " + a.getId() + " with ID " + g.getId());
    }
}
