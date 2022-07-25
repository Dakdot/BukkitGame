package me.dakdot.game.entity;

import lombok.Getter;
import me.dakdot.game.Arena;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Lightable;
import org.bukkit.event.player.PlayerInteractEvent;

public class UpgradeEntity extends GameEntity {

    @Getter private Location upgradeSignLocation;
    private Block sign;
    private BlockState originalSign;
    @Getter private Location indicatorLightLocation;

    private GeneratorEntity generatorEntity;
    private String entityName;

    int level = 1;

    public UpgradeEntity(String name, Location upgradeSignLocation, Location indicatorLightLocation, GeneratorEntity ge) {
        super(name);
        this.upgradeSignLocation = upgradeSignLocation;
        this.sign = upgradeSignLocation.getWorld().getBlockAt(upgradeSignLocation);
        this.originalSign = sign.getState();
        this.indicatorLightLocation = indicatorLightLocation;
        this.generatorEntity = ge;
        System.out.println(name + ", " + upgradeSignLocation  + ", " + sign  + ", " + indicatorLightLocation);
    }

    String genEntityName = "";
    int genEntityTeam = -1;

    public UpgradeEntity(String name, Location upgradeSignLocation, Location indicatorLightLocation, String genEntityName, int genEntityTeam) {
        super(name);
        this.upgradeSignLocation = upgradeSignLocation;
        this.sign = upgradeSignLocation.getWorld().getBlockAt(upgradeSignLocation);
        this.originalSign = sign.getState();
        this.indicatorLightLocation = indicatorLightLocation;
        this.generatorEntity = null;
        this.genEntityName = genEntityName;
        this.genEntityTeam = genEntityTeam;
    }

    public void init(Arena a) {
        super.init(a);

        if (generatorEntity == null) generatorEntity = (GeneratorEntity) arena.getEntity(genEntityTeam, genEntityName);
        if (generatorEntity == null) {
            System.err.println("Could not create upgrade entity");
            return;
        }

        if (!(sign instanceof Sign)) {
            System.err.println(sign);
            return;
        }
        Sign state = (Sign) sign.getState();
        state.setLine(0, "UPGRADE GENERATOR:");
        state.setLine(1, "Zombie");
        state.setLine(2, "50 points");
        state.update();

        System.out.println("Started UpgradeEntity with ID " + getId());
    }

    public void clean() {
        BlockState state = originalSign;
        // TODO: Make it go back to what it was lol
    }

    public void onInteract(PlayerInteractEvent e) {
        level++;
        generatorEntity.setLevel(level);

        Block light = indicatorLightLocation.getWorld().getBlockAt(indicatorLightLocation.clone().add(0, level - 1, 0));
        if (!(light.getBlockData() instanceof Lightable)) return;
        e.setCancelled(true);

        Lightable blockData = (Lightable) light.getBlockData();
        blockData.setLit(true);
        light.setBlockData(blockData);
    }

}
