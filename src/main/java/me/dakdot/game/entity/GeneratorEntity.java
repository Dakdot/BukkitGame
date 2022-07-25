package me.dakdot.game.entity;

import lombok.Getter;
import lombok.Setter;
import me.dakdot.game.Arena;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GeneratorEntity extends GameEntity {

    @Getter @Setter private Location location;
    @Getter @Setter private Location spawnLocation;
    @Getter @Setter private Material material;
    @Getter @Setter private transient int level = 1;

    private UpgradeEntity upgradeEntity = null;

    public GeneratorEntity(String name, Location location, Location spawnLocation, Material material) {
        super(name);
        this.spawnLocation = spawnLocation;
        this.location = location;
        this.material = material;
    }

    public void init(Arena a) {
        super.init(a);
        location.add(0.5, 0.0, 0.5);
        spawnLocation.add(0.5, 0.0, 0.5);
        System.out.println("Started GeneratorEntity with ID " + getId());
    }

    public void update(float dt) {
        for (Entity e : location.getWorld().getNearbyEntities(location, 0.5, 1, 0.5)) {
            if (!(e instanceof Player)) return;
            Player p = (Player) e;
            p.teleport(spawnLocation);
            p.getInventory().addItem(new ItemStack(material, level));
            p.updateInventory();
        }
    }

}
