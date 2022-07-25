package me.dakdot.game.entity;

import me.dakdot.game.Arena;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.PressureSensor;

import java.util.Collection;

public class CoinGeneratorEntity extends GameEntity {

    private Location location;
    private ItemStack coinStack;
    private Block plateBlock;

    int level = 1;

    public CoinGeneratorEntity(String name, Location location) {
        super(name);
        this.location = location;
        this.coinStack = new ItemStack(Material.SUNFLOWER);
        this.plateBlock = location.getBlock();

        ItemMeta meta = coinStack.getItemMeta();
        meta.setDisplayName("Coin");
        coinStack.setItemMeta(meta);
    }

    public void init(Arena a) {
    }

    public void update(float dt) {
        if (!(plateBlock.getBlockData() instanceof PressureSensor)) return;
        else System.out.println("ole"); // remove this lol
        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 1, 1, 1);
        if (entities.isEmpty()) return;
        for (Entity e : entities) {
            if (!(e instanceof Player)) continue;
            if (!((PressureSensor) plateBlock.getBlockData()).isPressed()) return;
            Player p = (Player) e;
            p.getInventory().addItem(coinStack);
            p.updateInventory();
        }
    }

    public void clean() {

    }
}
