package me.dakdot.game.entity;

import lombok.Getter;
import me.dakdot.game.Arena;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import java.util.List;

public class ShopEntity extends GameEntity {

    @Getter private Location location;
    @Getter private final EntityType entityType;
    @Getter private List<MerchantRecipe> selling;

    @Getter private LivingEntity entity;

    public ShopEntity(String name, Location location, EntityType entityType, List<MerchantRecipe> selling) {
        super(name);
        this.location = location;
        this.entityType = entityType;
        this.selling = selling;
    }

    public void init(Arena a) {
        super.init(a);
        entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        entity.setInvulnerable(true);
        entity.setPersistent(true);
        entity.setAI(false);
        System.out.println(selling.get(0).getResult());

        ((Villager) entity).setRecipes(selling);
        System.out.println("Spawned ShopEntity with ID " + getId());
    }

    public void clean() {
        entity.remove();
    }

}
