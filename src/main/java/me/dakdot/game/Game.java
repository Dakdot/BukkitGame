package me.dakdot.game;

import lombok.Getter;
import lombok.Setter;
import me.dakdot.game.entity.GameEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.util.*;

public class Game {

    /* WHAT WE NEED IN A GAME:
    The Game object is responsible for storing data about a specific round of the game.
    It will store the players in the game, the teams, and any other values (like scores).
    - The status of the game
    - The two teams
    - The arena being used

    * */

    public enum Status {
        STOPPING,
        STOPPED,
        LOBBY,
        STARTING,
        STARTED
    }

    @Getter private UUID id;
    @Getter @Setter private Status status = Status.STOPPED;

    public final Team aTeam = new Team();
    public final Team bTeam = new Team();

    private List listeners = new ArrayList();
    private BukkitTask task;

    private List<Map> originalPlayerData = new ArrayList<>();

    @Getter @Setter private Arena arena;

    public Game(Arena a) {
        this.id = UUID.randomUUID();
        this.arena = a;
    }

    public void start() {
        status = Status.STARTING;
        System.out.println("Starting game in arena " + arena.getId());
        for (GameEntity e : arena.getAllEntities()) e.init(arena);

        task = Main.plugin.getServer().getScheduler().runTaskTimer(Main.plugin, () -> {
            float start = System.nanoTime();
            float end = System.nanoTime();
            for (GameEntity e : arena.getAllEntities()) {
                e.update(end - start * (float)10E-6);
            }
            end = System.nanoTime();
        }, 0L, 1L);

        updateScoreboard();
        for (Player p : getAllPlayers()) movePlayer(p);
        status = Status.STARTED;
        System.out.println("Game in arena " + arena.getId() + " STARTED");
    }

    public void stop() {
        status = Status.STOPPING;
        System.out.println("Stopping game in arena " + arena.getId());

        task.cancel();
        for (GameEntity e : arena.getAllEntities()) e.clean();

        updateScoreboard();
        for (Player p : getAllPlayers()) movePlayer(p);
        status = Status.STOPPED;
        System.out.println("Game in arena " + arena.getId() + " STOPPED");
    }

    public void addPlayer(Player p, Team t) {
        t.players.add(p);
        for (Object o : listeners) if (o instanceof GameJoinListener) ((GameJoinListener) o).onPlayerJoin(p);
    }

    public void removePlayer(Player p) {
        aTeam.players.remove(p);
        bTeam.players.remove(p);

        if (getAllPlayers().isEmpty()) {
            System.out.println("Game in arena " + arena.getId() + " has no players. Stopping game.");
            stop();
        }
    }

    public Team getTeamFromPlayer(Player p) {
        return aTeam.players.contains(p) ? aTeam : bTeam;
    }

    public List<Player> getAllPlayers() {
        List<Player> res = new ArrayList<>();
        res.addAll(aTeam.players);
        res.addAll(bTeam.players);
        return res;
    }

    public void registerListener(GameListener l) {
        listeners.add(l);
    }

    public void updateScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("scores", "dummy", "Scores");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score aTeamScore = objective.getScore("A Team");
        aTeamScore.setScore(aTeam.score);
        Score bTeamScore = objective.getScore("B Team");
        bTeamScore.setScore(bTeam.score);

        for (Player p : getAllPlayers()) {
            if (status == Status.STOPPING) p.setScoreboard(manager.getMainScoreboard());
            else p.setScoreboard(board);
        }
    }

    public void movePlayer(Player p) {
        switch (status) {
            case LOBBY:
                Map map = new HashMap();
                map.put("name", p.getName());
                map.put("inventory", p.getInventory());
                map.put("experience", p.getExp());
                map.put("location", p.getLocation());
                originalPlayerData.add(map);
                p.teleport((getTeamFromPlayer(p) == aTeam) ?
                        arena.getLocation(1, "lobbySpawn") : arena.getLocation(2, "lobbySpawn"));
                p.getInventory().clear();
                p.setExp(0);
                break;
            case STARTING:
                p.teleport((getTeamFromPlayer(p) == aTeam) ?
                        arena.getLocation(1, "arenaSpawn") : arena.getLocation(2, "arenaSpawn"));
                break;
            case STOPPING:
                for (Map map1 : originalPlayerData) {
                    if (map1.get("name").equals(p.getName())) {
                        p.teleport((Location) map1.get("location"));
                        p.setExp((float) map1.get("experience"));
                        p.getInventory().setContents(((PlayerInventory) map1.get("inventory")).getContents());
                        p.updateInventory();
                    }
                }
                break;

        }
    }

}
