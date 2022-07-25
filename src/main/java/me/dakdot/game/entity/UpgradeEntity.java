package me.dakdot.game.entity;

import lombok.Getter;
import me.dakdot.game.Arena;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.event.player.PlayerInteractEvent;

public class UpgradeEntity extends GameEntity {

    @Getter private final Location upgradeSignLocation;
    private final Block sign;
    private final BlockData originalSign;
    @Getter private final Location indicatorLightLocation;

    @Getter private GeneratorEntity generatorEntity;
    private String entityName;

    int level = 1;

    public UpgradeEntity(String name, Location upgradeSignLocation, Location indicatorLightLocation, GeneratorEntity ge) {
        super(name);
        this.upgradeSignLocation = upgradeSignLocation;
        this.sign = upgradeSignLocation.getBlock();
        this.originalSign = sign.getBlockData();
        this.indicatorLightLocation = indicatorLightLocation;
        this.generatorEntity = ge;
        System.out.println(name + ", " + upgradeSignLocation  + ", " + sign  + ", " + indicatorLightLocation);
    }

    String genEntityName = "";
    int genEntityTeam = -1;

    public UpgradeEntity(String name, Location upgradeSignLocation, Location indicatorLightLocation, String genEntityName, int genEntityTeam) {
        super(name);
        this.upgradeSignLocation = upgradeSignLocation;
        this.sign = upgradeSignLocation.getBlock();
        this.originalSign = sign.getBlockData();
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

        if (!(sign.getState() instanceof Sign state)) return;
        state.setLine(0, "UPGRADE GENERATOR:");
        state.setLine(1, "Zombie");
        state.setLine(2, "50 points");
        state.update();

        System.out.println("Started UpgradeEntity with ID " + getId());
    }

    @Override
    public void update(float dt) {
        Block light = indicatorLightLocation.getBlock();
        for (int i = 0; i < level; i++) {
            if (!(light.getBlockData() instanceof Lightable blockData)) return;
            if (blockData.isLit()) continue;
            blockData.setLit(true);
            light.setBlockData(blockData);
            light = light.getRelative(BlockFace.UP);
        }
    }

    public void clean() {
        sign.getState().setBlockData(originalSign);
        sign.getState().update();
    }

    public void onInteract(PlayerInteractEvent e) {
        level++;
        generatorEntity.setLevel(level);
        e.setCancelled(true);
    }

}
