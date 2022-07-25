package me.dakdot.game.entity;

import lombok.Getter;
import me.dakdot.game.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.PressureSensor;

import java.util.Collection;

public class CoinGeneratorEntity extends GameEntity {

    @Getter
    private Location location;
    private ItemStack coinStack;
    private Block plateBlock;

    int level = 1;
    boolean given = false;
    boolean powered = false;

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
        BlockData data = Bukkit.createBlockData("minecraft:light_weighted_pressure_plate[power=0]");
        System.out.println(data);
    }

    public void update(float dt) {
        if (!plateBlock.getBlockData().getMaterial().getKey().getKey().contains("pressure_plate")) return; // not a pressure plate

        if (plateBlock.getBlockData() instanceof Powerable powerable) powered = powerable.isPowered();
        else if (plateBlock.getBlockData() instanceof AnaloguePowerable analoguePowerable) powered = analoguePowerable.getPower() > 0;
        else return; // ^^ get powered state of plate (works for both normal and weighted plates)

        if (given && !powered) given = false;

        assert location.getWorld() != null; // ensure location world is not null
        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 1, 1, 1); // get nearby entities
        if (entities.isEmpty()) return; // no entities around

        for (Entity e : entities) {
            if (!(e instanceof Player p)) continue; // find player within entity list
            if (powered && !given) {
                p.getInventory().addItem(coinStack);
                p.updateInventory();
                given = true;
            }
        }
    }

    public void clean() {

    }
}
