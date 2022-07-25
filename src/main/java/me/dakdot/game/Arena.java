package me.dakdot.game;

import lombok.Getter;
import lombok.Setter;
import me.dakdot.game.entity.GameEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

public class Arena {

    @Getter @Setter
    private String name;
    @Getter
    private int id = -1;
    private transient File file;

    @Getter
    private Map<String, Location>[] locations;
    @Getter
    private List<GameEntity>[] entities;


    /* What do we need in an arena (will add to this list lol):
    - A name for it (so that players can easily refer to them)
    - A UUID (so that the SYSTEM can easily refer to them, and also so that names aren't the only reference to arenas (e.g. can have duplicate arenas))

    - For each team:
        - A spawn location (i.e. the bedroom)
        - The four spawner locations
        - A shop (with locations where each vendor entity will spawn)
        - An upgrades room (with locations for each button)
        - Locations for each of the mob spawners/platforms
    * */

    /* THERE WILL BE TWO CLASSES: Arena and Game
    Game will hold any data pertinent to just the game (for example the players in the game, the teams, the points each team has, etc)
        - Game will contain a reference to an Arena, as a Game needs to happen in an arena
    Arena will hold any data for the physical building of the arena (like locations, entities, and any other properties unique to an arena)
        - Arena will have NO KNOWLEDGE of any game objects, since it is largely just a data class

    THERE WILL ALSO BE TWO MANAGERS WHICH MANAGE EACH
    GameManager is responsible for starting and stopping games, matching players to games, etc
        - GameManager will ensure that only one game is using an arena
    ArenaManager is responsible for loading arenas into memory, and providing the Game objects with their respective Arena objects
    * */

    public Arena(int teamsAmt) {
        locations = new Map[teamsAmt];
        entities = new List[teamsAmt];
        for (int i = 0 ; i < locations.length; i++) locations[i] = new HashMap<>();
        for (int i = 0 ; i < entities.length; i++) entities[i] = new ArrayList<>();
    }

    public Arena(String name, int teamsAmt) {
        this.name = name;
        locations = new Map[teamsAmt];
        entities = new List[teamsAmt];
        for (int i = 0 ; i < locations.length; i++) locations[i] = new HashMap<>();
        for (int i = 0 ; i < entities.length; i++) entities[i] = new ArrayList<>();

    }

    public void setID(int id) {
        if (this.id < 0) this.id = id;
        else throw new IllegalStateException("Arena with ID " + id + " is already set.");
    }

    public Location getLocation(int team, String key) {
        return locations[team - 1].get(key);
    }

    public void addLocation(int team, String key, Location l) { locations[team - 1].put(key, l); }

    public GameEntity getEntity(int team, String key) {
        for (GameEntity e : getAllEntities()) if (e.getName().equals(key)) return e;
        return null;
    }

    public List<GameEntity> getAllEntities() {
        List<GameEntity> result = new ArrayList<>();
        for (int i = 0; i < entities.length; i++) {
            result.addAll(entities[i]);
        }
        return result;
    }

    public List<Location> getAllLocations() {
        List<Location> result = new ArrayList<>();
        for (int i = 0; i < locations.length; i++) {
            result.addAll(locations[i].values());
        }
        return result;
    }

    public void addEntity(int team, GameEntity e) { entities[team - 1].add(e); }

    public File getFile() { return file; }
    public void setFile(File f) { this.file = f; }

    public ItemStack getCoinItemStack(int amount) {
        ItemStack coinStack = new ItemStack(Material.SUNFLOWER, amount);
        ItemMeta im = coinStack.getItemMeta();
        im.setDisplayName("Coin");
        coinStack.setItemMeta(im);
        return coinStack;
    }

}
