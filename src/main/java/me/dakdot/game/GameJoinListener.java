package me.dakdot.game;

import org.bukkit.entity.Player;

public class GameJoinListener implements GameListener {

    private Game g;

    public GameJoinListener(Game g) {
        this.g = g;
    }

    public void onPlayerJoin(Player p) {
        int aTeamSize = g.aTeam.players.size();
        int bTeamSize = g.bTeam.players.size();

        if (aTeamSize >= 4 && bTeamSize >= 4) {
            System.out.println("Start permissives met for game in arena + " + g.getArena().getId());
            g.start();
        }
    }

}
