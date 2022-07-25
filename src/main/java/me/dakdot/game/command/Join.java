package me.dakdot.game.command;

import me.dakdot.game.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class Join extends Subcommand{

    private Random random = new Random();

    public Join() {
        this.name = "join";
        this.description = "Joins a game in the specified arena.";
        this.aliases = new String[] { "j" };
        this.console = false;
        this.arguments = new String[] { "<arena>" };
    }

    @Override
    public void onRun(CommandSender s, String[] args) {
        if (args.length != 1) {
            showUsageMessage(s);
            return;
        }

        Arena a = ArenaManager.getArenasByName(args[0])[0];
        if (a == null) {
            MessageManager.error(s, "No arena with name " + args[0] + " exists.");
            return;
        }

        Game g = GameManager.get().getGame(a);
        Player p = (Player) s;
        if (g == null) {
            g = GameManager.get().createGame(a);
            System.out.println("Created new game in arena " + a.getId() + "; Status is " + g.getStatus());
        }

        if (g.aTeam.players.size() <= g.bTeam.players.size()) g.addPlayer(p, g.aTeam);
        else g.addPlayer(p, g.bTeam);

        g.movePlayer(p);
        MessageManager.info(p, "You're on the " + ((g.getTeamFromPlayer(p) == g.aTeam) ? "&4red" : "&3blue") + "&r team!");
    }
}
