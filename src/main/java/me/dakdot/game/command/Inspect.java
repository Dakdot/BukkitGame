package me.dakdot.game.command;

import me.dakdot.game.*;
import me.dakdot.game.entity.GameEntity;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class Inspect extends Subcommand {

    public Inspect() {
        this.name = "inspect";
        this.description = "Shows detailed information regarding game elements (arenas, entities, etc).";
        this.console = true;
        this.aliases = new String[] { "i" };
        this.arguments = new String[] { "[arena]" };
    }

    @Override
    public void onRun(CommandSender s, String[] args) {
        if (args.length == 0) {
            MessageManager.info(s, "There are " + ArenaManager.getArenas().size() + " arenas.");
            if (ArenaManager.getArenas().isEmpty()) return;

            StringBuilder st = new StringBuilder();
            for (Arena a : ArenaManager.getArenas()) st.append("[").append(a.getName()).append(", ").append(a.getId()).append("] ");
            MessageManager.info(s, st.toString().trim());
            return;
        }
        List<Arena> arenas = ArenaManager.matchArena(args[0]);
        if (arenas.isEmpty()) {
            MessageManager.error(s, "Could not find an arena with ID or name \"" + args[0] + "\".");
            return;
        } else if (arenas.size() > 1) {
            MessageManager.error(s, "Multiple arenas with the name " + args[0] + " exist. Please run this command again and specify an ID instead of a name.");
            for (Arena arena : arenas) {
                MessageManager.warn(s, " - ID: " + arena.getId() + ", Name: " + args[0]);
            }
            return;
        }

        Arena a = arenas.get(0);
        showInspect(s, a);
    }

    private void showInspect(CommandSender s, Arena a) {
        MessageManager.info(s, "&4SHOWING ARENA INSPECT:");
        MessageManager.info(s, " - ID: " + a.getId());
        MessageManager.info(s, " - File: " + a.getFile().getName());
        MessageManager.info(s, " - Name: " + a.getName());
        MessageManager.info(s, " - Game Status: " + ((GameManager.get().getGame(a) == null) ? "&c&ono game" : GameManager.get().getGame(a).getStatus()));
        if (!a.getAllLocations().isEmpty()) {
            MessageManager.info(s, " - Locations:");
            for (Map<String, Location> map : a.getLocations()) {
                for (String key : map.keySet()) {
                    Location l = map.get(key);
                    MessageManager.info(s, "    - " + key + ": " + l.getX() + ", " + l.getY() + ", " + l.getZ());
                }
            }
        } else MessageManager.info(s, "&o no defined locations");

        if (!a.getAllEntities().isEmpty()) {
            MessageManager.info(s, " - Entities:");
            for (GameEntity e : a.getAllEntities()) {
                MessageManager.info(s, "    - " + e.getName() + ": " + e.getClass().getSimpleName());
            }
        } else MessageManager.info(s, "&o no defined entities");
    }
}
