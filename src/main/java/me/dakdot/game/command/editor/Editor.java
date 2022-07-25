package me.dakdot.game.command.editor;

import me.dakdot.game.Arena;
import me.dakdot.game.ArenaManager;
import me.dakdot.game.MessageManager;
import me.dakdot.game.command.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class Editor extends Subcommand implements Listener {

    private List<Subcommand> subcommandList = new ArrayList<>();

    private boolean launched = false;
    private Arena currentArena;

    private static Editor instance; // Singleton stuff
    public static Editor get() {
        if (instance == null) instance = new Editor();
        return instance;
    }

    public Editor() {
        this.name = "editor";
        this.aliases = new String[] { "e" };
        this.arguments = new String[] { "<arena>" };
        this.console = false;
        this.description = "Launches the arena editor.";

        subcommandList.add(new EditorHelp());
        subcommandList.add(new EditorCreateEntity());
    }

    @Override
    public void onRun(CommandSender s, String[] args) {
        if (args.length == 0) {
            getCommand("help").onRun(s, args);
            return;
        }

        Subcommand c = getCommand(args[0]);

        if (c == null) {
            List<Arena> arenas = ArenaManager.matchArena(args[0]);
            if (!arenas.isEmpty() && !launched) {
                Arena a = arenas.get(0);
                MessageManager.info(s, "Launching editor on arena " + a.getId() + ". Type 'exit' to quit.");
                launched = true;
                currentArena = a;
                return;
            }

            getCommand("help").onRun(s, args);
            return;
        }

        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);

        if (!(s instanceof Player)) {
            if (c.console) {
                c.onRun(s, newArgs);
            } else {
                MessageManager.error(s, "This command cannot be run by the console.");
                return;
            }
        } else {
            c.onRun(s, newArgs);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (launched) e.setCancelled(true);
        else return;
        if (e.getMessage().equalsIgnoreCase("exit")) {
            launched = false;
            currentArena = null;
            MessageManager.info(e.getPlayer(), "Editor quit. Chat is all yours.");
            return;
        }

        onRun(e.getPlayer(), e.getMessage().split(" "));
    }

    public static Subcommand getCommand(String s) {
        for (Subcommand c : get().subcommandList) {
            if (c.name.equalsIgnoreCase(s)) return c;
            else if (c.aliases != null && List.of(c.aliases).contains(s)) return c;
        }
        return null;
    }

    public static Arena getCurrentArena() {
        return get().currentArena;
    }

    public static List<Subcommand> getCommands() {
        return get().subcommandList;
    }
}
