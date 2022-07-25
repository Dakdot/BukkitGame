package me.dakdot.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dakdot.game.entity.*;
import me.dakdot.game.serialization.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ArenaManager {

    private static ArenaManager instance; // Singleton stuff
    public static ArenaManager get() {
        if (instance == null) instance = new ArenaManager();
        return instance;
    }

    private List<Arena> arenas = new ArrayList<>();
    private Gson gson;
    private File folder;

    public void init() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        //builder.registerTypeHierarchyAdapter(ConfigurationSerializable.class, new SerializableTypeAdapter());
        builder.registerTypeHierarchyAdapter(Location.class, new LocationTypeAdapter());
        builder.registerTypeHierarchyAdapter(ItemStack.class, new ItemStackTypeAdapter());
        builder.registerTypeHierarchyAdapter(BoundingBox.class, new BoundingBoxTypeAdapter());

        builder.registerTypeHierarchyAdapter(GameEntity.class, new GameEntityTypeAdapter());
        builder.registerTypeHierarchyAdapter(PlatformEntity.class, new PlatformEntityTypeAdapter());
        builder.registerTypeHierarchyAdapter(GeneratorEntity.class, new GeneratorEntityTypeAdapter());
        builder.registerTypeHierarchyAdapter(ShopEntity.class, new ShopEntityTypeAdapter());
        builder.registerTypeHierarchyAdapter(UpgradeEntity.class, new UpgradeEntityTypeAdapter());
        builder.registerTypeHierarchyAdapter(CoinGeneratorEntity.class, new CoinGeneratorEntityTypeAdapter());
        gson = builder.create();

        folder = new File(Main.plugin.getDataFolder(), "arenas");
        reload();
    }

    public static void reload() {
        get().folder.mkdirs();
        get().arenas = new ArrayList<>();

        if (get().folder.listFiles() == null || get().folder.listFiles().length == 0) return;

        for (File f : get().folder.listFiles()) {
            if (f.isDirectory()) continue;
            if (!f.getName().endsWith(".json")) continue;

            try {
                Reader r = new FileReader(f);

                Arena a = get().gson.fromJson(r, Arena.class);
                a.setFile(f);
                get().arenas.add(a);
                Main.plugin.getLogger().log(Level.INFO, "Loaded arena \"" + a.getName() + "\" (ID " + a.getId() + ")");
                r.close();
            } catch (IOException e) {
                Main.plugin.getLogger().log(Level.SEVERE, "Couldn't load arena file " + f.getName(), e);
            }
        }
    }

    public static void save() {
        get().folder.mkdirs();

        for (Arena a : get().arenas) {
            try {
                File f = a.getFile();
                Writer w = new FileWriter(f);
                get().gson.toJson(a, w);
                w.close();
            } catch (IOException e) {
                Main.plugin.getLogger().log(Level.SEVERE, "Couldn't save arena file for arena: " + a.getName(), e);
            }
        }
    }

    // TODO: implement teams amt
    public static Arena createArena(String name) {
        Arena arena = new Arena(name, 2); // hardcoded 2 team for now lol
        int numArenas = get().arenas.size();
        for (int i = 0; i < numArenas + 1; i++) {
            if (getArena(i) == null) {
                arena.setID(i);
                File f = new File(get().folder, arena.getName() + ".json");
                int suffix = 2;
                while (f.exists()) {
                    f = new File(get().folder, arena.getName() + "_" + i + ".json");
                    suffix++;
                }
                arena.setFile(f);
                get().arenas.add(arena);
            }
        }
        return arena;
    }

    public static List<Arena> matchArena(String query) {
        List<Arena> result = new ArrayList<>();
        int number = NumberUtils.toInt(query, -1);
        for (Arena a : get().arenas) {
            if (a.getName().equals(query)) result.add(a);
            if (number >= 0 && (a.getId() == number) && !result.contains(a)) result.add(a);
        }
        return result;
    }

    public static List<Arena> getArenas() {
        return get().arenas;
    }

    public static Arena[] getArenasByName(String name) {
        List<Arena> list = new ArrayList<>();
        for (Arena a : get().arenas) if (a.getName().equals(name)) list.add(a);
        return list.toArray(new Arena[0]);
    }

    public static Arena[] getArenasByNameOrId(String s) {
        List<Arena> list = new ArrayList<>();
        for (Arena a : get().arenas) if (a.getName().equals(s) || a.getId() == NumberUtils.toInt(s, -1)) list.add(a);
        return list.toArray(new Arena[0]);
    }

    public static Arena getArena(int id) {
        for (Arena a : get().arenas) if (a.getId() == id) return a;
        return null;
    }

}
