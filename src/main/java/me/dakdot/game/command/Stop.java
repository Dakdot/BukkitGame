package me.dakdot.game.command;

import me.dakdot.game.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.CommandSender;

public class Stop extends Subcommand {

    public Stop() {
        this.name = "stop";
        this.description = "Stops a game in progress.";
        this.console = true;
        this.arguments = new String[] { "<arena>" };
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
            MessageManager.error(s, "Cannot stop game in arena " + a.getId() + ": no game exists.");
            return;
        }

        GameManager.get().removeGame(a);
        MessageManager.info(s, "Stopped game in arena " + a.getId() + " with ID " + g.getId());
    }
}
