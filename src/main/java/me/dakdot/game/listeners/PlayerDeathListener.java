package me.dakdot.game.listeners;

import me.dakdot.game.Game;
import me.dakdot.game.GameManager;
import me.dakdot.game.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Game g = GameManager.get().getGameFromPlayer(p);
        if (g == null) return;
        if (g.getStatus() != Game.Status.STARTED) return;

        Team t = g.getTeamFromPlayer(p);
        if (t == g.aTeam) g.bTeam.score += 100;
        else g.aTeam.score += 100;
        g.updateScoreboard();
    }

}
