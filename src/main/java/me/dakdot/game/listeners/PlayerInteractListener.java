package me.dakdot.game.listeners;

import me.dakdot.game.Arena;
import me.dakdot.game.Game;
import me.dakdot.game.GameManager;
import me.dakdot.game.entity.GameEntity;
import me.dakdot.game.entity.UpgradeEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Game g = GameManager.get().getGameFromPlayer(p);
        if (g == null) return;
        if (g.getStatus() != Game.Status.STARTED) return;

        Arena a = g.getArena();
        for (GameEntity ge : a.getAllEntities()) {
            if (!(ge instanceof UpgradeEntity)) continue;
            UpgradeEntity ue = (UpgradeEntity) ge;
            if (e.getClickedBlock() == null) return;
            if (!ue.getUpgradeSignLocation().equals(e.getClickedBlock().getLocation())) return;
            ue.onInteract(e);
            return;
        }
    }

}
