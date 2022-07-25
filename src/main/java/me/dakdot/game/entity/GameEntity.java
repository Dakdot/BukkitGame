package me.dakdot.game.entity;

import lombok.Getter;
import lombok.Setter;
import me.dakdot.game.Arena;

import java.util.UUID;

public abstract class GameEntity {

    @Getter @Setter protected String name;
    @Getter protected UUID id;
    @Getter protected Arena arena = null;

    public GameEntity(String name) {
        this.name = name;
        id = UUID.randomUUID();
    }

    public void init(Arena a) {
        this.arena = a;
    }
    public void update(float dt) { }
    public void clean() {
        this.arena = null;
    }

    /* WHAT ENTITIES NEED:
    - A name (not technically, but useful for debugging)
    - A UUID (necessary for the system to refer to it by)

    - An init() method:
        - This will allow any entities to do initialization before the game starts
        - Useful for entities that need to spawn in Minecraft entities for themselves

    - An update(float dt) method:
        - This will update the entity and provide a delta time (useful if the server is running behind)
        - The default definition of this method will do nothing (useful is the entity is solely cosmetic and needs no logic)

    - A clean() method:
        - This method allows the entity to clean up after itself once a game is finished
        - Allows entities with Minecraft entities to remove them so they're not taking up server resources in an unused arena
    * */


}
