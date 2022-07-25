package me.dakdot.game.listeners;

import me.dakdot.game.Game;
import me.dakdot.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Game g = GameManager.get().getGameFromPlayer(p);
        if (g == null) return;

        g.removePlayer(p);
    }

}
