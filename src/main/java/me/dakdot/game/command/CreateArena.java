package me.dakdot.game.command;

import me.dakdot.game.Arena;
import me.dakdot.game.ArenaManager;
import me.dakdot.game.Main;
import me.dakdot.game.entity.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class CreateArena extends Subcommand {

    public CreateArena() {
         this.name = "create";
         this.description = "Creates an arena file with the specified name.";
         this.console = true;
         this.arguments = new String[] { "<name>" };
    }

    @Override
    public void onRun(CommandSender s, String[] args) {
        if (args.length != 1) showUsageMessage(s);

        Arena a = ArenaManager.createArena(args[0]);
        System.out.println("Created arena " + a.getId());

        a.addEntity(1, new PlatformEntity("platformLeft",
                new Location(Main.plugin.getServer().getWorld("world"), 40, 57, -41),
                new Location(Main.plugin.getServer().getWorld("world"), 58, 57, -41)));
        System.out.println("Created and added platformLeft 1");


        a.addEntity(1, new PlatformEntity("platformRight",
                new Location(Main.plugin.getServer().getWorld("world"), 40, 57, -35),
                new Location(Main.plugin.getServer().getWorld("world"), 58, 57, -35)));
        System.out.println("Created and added platformRight 1");


        a.addEntity(2, new PlatformEntity("platformLeft",
                new Location(Main.plugin.getServer().getWorld("world"), 16.0, 63.0, 0.0),
                new Location(Main.plugin.getServer().getWorld("world"), 0.5, 63.0, 0.5)));
        System.out.println("Created and added platformLeft 2");


        a.addEntity(2,  new PlatformEntity("platformRight",
                new Location(Main.plugin.getServer().getWorld("world"), 16.0, 63.0, 6.0),
                new Location(Main.plugin.getServer().getWorld("world"), 0.5, 63.0, 6.5)));
        System.out.println("Created and added platformRight 2");



        a.addEntity(1, new GeneratorEntity("genZombie",
                new Location(Main.plugin.getServer().getWorld("world"), 26, 56, -47),
                new Location(Main.plugin.getServer().getWorld("world"), 26, 56, -43),
                Material.ROTTEN_FLESH));
        System.out.println("Created and added genZombie 1");


        a.addEntity(1, new GeneratorEntity("genSkeleton",
                new Location(Main.plugin.getServer().getWorld("world"), 32, 56, -47),
                new Location(Main.plugin.getServer().getWorld("world"), 32, 56, -43),
                Material.BONE));
        System.out.println("Created and added genSkeleton 1");


        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.ZOMBIE_SPAWN_EGG, 1), 999999);
        recipe.addIngredient(new ItemStack(Material.ROTTEN_FLESH, 5));
        recipe.addIngredient(new ItemStack(Material.SUNFLOWER, 20));
        List<MerchantRecipe> recipesList = new ArrayList<>();
        recipesList.add(recipe);
        a.addEntity(1, new ShopEntity("shopEntity1",
                new Location(Main.plugin.getServer().getWorld("world"), 1.0, 63.0, 1.0),
                EntityType.VILLAGER, recipesList));

        recipe = new MerchantRecipe(new ItemStack(Material.SKELETON_SPAWN_EGG, 1), 999999);
        recipe.addIngredient(new ItemStack(Material.BONE, 4));
        recipe.addIngredient(new ItemStack(Material.SUNFLOWER, 20));
        recipesList = new ArrayList<>();
        recipesList.add(recipe);
        a.addEntity(1, new ShopEntity("shopEntity2",
                new Location(Main.plugin.getServer().getWorld("world"), -4.0, 63.0, 1.0),
                EntityType.VILLAGER, recipesList));


        a.addEntity(1, new UpgradeEntity("upgradeZombie", new Location(Main.plugin.getServer().getWorld("world"), 17, 57, -43),
                new Location(Main.plugin.getServer().getWorld("world"), 16, 58, -43),
                (GeneratorEntity) a.getEntity(1, "genZombie")));

        a.addEntity(2, new CoinGeneratorEntity("coinGen1", new Location(Main.plugin.getServer().getWorld("world"), 56, 64, 51)));




        a.addLocation(1, "lobbySpawn", new Location(Main.plugin.getServer().getWorld("world"), 11.5, 56, -35.5));
        a.addLocation(2, "lobbySpawn", new Location(Main.plugin.getServer().getWorld("world"), 11.5, 56, -40.5));

        a.addLocation(1, "arenaSpawn", new Location(Main.plugin.getServer().getWorld("world"), -40.5, 56, -38.5));
        a.addLocation(2, "arenaSpawn", new Location(Main.plugin.getServer().getWorld("world"), 58.5, 56, -38.5));




        ArenaManager.save();
    }
}
