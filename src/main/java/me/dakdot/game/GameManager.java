package me.dakdot.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static GameManager instance; // Singleton stuff
    public static GameManager get() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    private final List<Game> games = new ArrayList<>();

    public void init() {

    }

    public Game createGame(Arena a) {
        if (getGame(a) != null) throw new IllegalStateException("Game already exists in arena " + a.getId());

        Game g = new Game(a);
        games.add(g);
        g.setStatus(Game.Status.LOBBY);
        return g;
    }

    public void removeGame(Arena a) {
        Game g = getGame(a);
        g.stop();
        games.remove(g);
    }

    public Game getGame(Arena a) {
        for (Game g : games) if (g.getArena().equals(a)) return g;
        return null;
    }

    public Game getGameFromPlayer(Player p) {
        for (Game g : games) if (g.getAllPlayers().contains(p)) return g;
        return null;
    }

}
