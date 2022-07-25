package me.dakdot.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    /* TEAM OBJECT:
    - Holds information regarding a single team of a game.
    - The score of the team
    - The players on the team

    * */

    public List<Player> players = new ArrayList<>();
    public int score;

}
