package me.dakdot.game.command;

import me.dakdot.game.Main;
import me.dakdot.game.MessageManager;
import me.dakdot.game.command.editor.Editor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandManager implements CommandExecutor {

    private List<Subcommand> commands = new ArrayList<>();

    public CommandManager() { }

    private static CommandManager instance; // Singleton stuff
    public static CommandManager get() {
        if (instance == null) instance = new CommandManager();
        return instance;
    }

    private Map<String, Object> uniforms;

    public void init() {
        commands.add(new Help());
        commands.add(new Reload());
        commands.add(new Inspect());
        commands.add(new CreateArena());
        Main.plugin.getServer().getPluginManager().registerEvents(Editor.get(), Main.plugin);
        commands.add(Editor.get());
        commands.add(new Start());
        commands.add(new Stop());
        commands.add(new Join());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            getCommand("help").onRun(sender, args);
            return true;
        }

        Subcommand c = getCommand(args[0]);

        if (c == null) {
            getCommand("help").onRun(sender, args);
            return true;
        }

        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);

        if (!(sender instanceof Player)) {
            if (c.console) {
                c.onRun(sender, newArgs);
            } else {
                MessageManager.error(sender, "This command cannot be run by the console.");
                return true;
            }
        } else {
            c.onRun(sender, newArgs);
        }

        return true;
    }

    public static Subcommand getCommand(String label) {
        for (Subcommand c : get().commands) {
            if (c.name.equalsIgnoreCase(label)) return c;
            else if (c.aliases != null && List.of(c.aliases).contains(label)) return c;
        }
        return null;
    }

    public List<Subcommand> getCommands() { return commands; }

    // TODO: Make "set" and "remove" booleans so we can know if the object was actually affected
    public static void setUniform(String name, Object o) { get().uniforms.put(name, o); }
    public static void removeUniform(String name) { get().uniforms.remove(name); }
    public static Object getUniform(String name) { return get().uniforms.get(name); }
    public static boolean uniformExists(String name) { return get().uniforms.containsKey(name); }

}
