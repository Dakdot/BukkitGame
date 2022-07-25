package me.dakdot.game.entity;

import lombok.Getter;
import lombok.Setter;
import me.dakdot.game.Arena;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlatformEntity extends GameEntity {

    @Getter
    @Setter
    private Location location;
    @Getter
    @Setter
    private Location spawnLocation;

    private final Random random = new Random();

    private List<Entity> entities = new ArrayList<>();

    public PlatformEntity(String name, Location location, Location spawnLocation) {
        super(name);
        this.location = location;
        this.spawnLocation = spawnLocation;
    }

    public void init(Arena a) {
        super.init(a);
        location.add(0.5, 0.0, 0.5);
        spawnLocation.add(0.5, 0.0, 0.5);
        System.out.println("Started PlatformEntity with ID " + getId());
    }

    public void clean() {
        for (Entity e : entities) {
            e.remove();
        }
    }

    @Override
    public void update(float dt) {
        for (Entity e : location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5)) {
            if (e instanceof Item) {
                ItemStack stack = ((Item) e).getItemStack();
                EntityType entityType;
                switch (stack.getType()) {
                    case BLAZE_SPAWN_EGG: entityType = EntityType.BLAZE; break;
                    case CAVE_SPIDER_SPAWN_EGG: entityType = EntityType.CAVE_SPIDER; break;
                    case CREEPER_SPAWN_EGG: entityType = EntityType.CREEPER; break;
                    case DROWNED_SPAWN_EGG: entityType = EntityType.DROWNED; break;
                    case ELDER_GUARDIAN_SPAWN_EGG: entityType = EntityType.ELDER_GUARDIAN; break;
                    case ENDERMAN_SPAWN_EGG: entityType = EntityType.ENDERMAN; break;
                    case ENDERMITE_SPAWN_EGG: entityType = EntityType.ENDERMITE; break;
                    case EVOKER_SPAWN_EGG: entityType = EntityType.EVOKER; break;
                    case GHAST_SPAWN_EGG: entityType = EntityType.GHAST; break;
                    case GUARDIAN_SPAWN_EGG: entityType = EntityType.GUARDIAN; break;
                    case HOGLIN_SPAWN_EGG: entityType = EntityType.HOGLIN; break;
                    case HUSK_SPAWN_EGG: entityType = EntityType.HUSK; break;
                    case MAGMA_CUBE_SPAWN_EGG: entityType = EntityType.MAGMA_CUBE; break;
                    case PHANTOM_SPAWN_EGG: entityType = EntityType.PHANTOM; break;
                    case PIGLIN_SPAWN_EGG: entityType = EntityType.PIGLIN; break;
                    case PIGLIN_BRUTE_SPAWN_EGG: entityType = EntityType.PIGLIN_BRUTE; break;
                    case PILLAGER_SPAWN_EGG: entityType = EntityType.PILLAGER; break;
                    case RAVAGER_SPAWN_EGG: entityType = EntityType.RAVAGER; break;
                    case SKELETON_SPAWN_EGG: entityType = EntityType.SKELETON; break;
                    case SLIME_SPAWN_EGG: entityType = EntityType.SLIME; break;
                    case SPIDER_SPAWN_EGG: entityType = EntityType.SPIDER; break;
                    case STRAY_SPAWN_EGG: entityType = EntityType.STRAY; break;
                    case VEX_SPAWN_EGG: entityType = EntityType.VEX; break;
                    case VINDICATOR_SPAWN_EGG: entityType = EntityType.VINDICATOR; break;
                    case WARDEN_SPAWN_EGG: entityType = EntityType.WARDEN; break;
                    case WITCH_SPAWN_EGG: entityType = EntityType.WITCH; break;
                    case WITHER_SKELETON_SPAWN_EGG: entityType = EntityType.WITHER_SKELETON; break;
                    case ZOGLIN_SPAWN_EGG: entityType = EntityType.ZOGLIN; break;
                    case ZOMBIE_SPAWN_EGG: entityType = EntityType.ZOMBIE; break;
                    case ZOMBIE_VILLAGER_SPAWN_EGG: entityType = EntityType.ZOMBIE_VILLAGER; break;
                    case ZOMBIFIED_PIGLIN_SPAWN_EGG: entityType = EntityType.ZOMBIFIED_PIGLIN; break;
                    default: continue;
                }
                for (int i = 0; i < stack.getAmount(); i++) {
                    entities.add(spawnLocation.getWorld().spawnEntity(spawnLocation, entityType));
                }
                spawnLocation.getWorld().playSound(spawnLocation, Sound.ITEM_TOTEM_USE, SoundCategory.MASTER, 100, 100);
                spawnLocation.getWorld().spawnParticle(Particle.TOTEM, spawnLocation, 100);
                e.remove();
            }
        }
    }

}
