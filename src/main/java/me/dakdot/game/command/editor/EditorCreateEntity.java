package me.dakdot.game.command.editor;

import me.dakdot.game.Arena;
import me.dakdot.game.ArenaManager;
import me.dakdot.game.MessageManager;
import me.dakdot.game.command.Subcommand;
import me.dakdot.game.entity.ShopEntity;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class EditorCreateEntity extends Subcommand {

    public EditorCreateEntity() {
        this.name = "createEntity";
        this.arguments = new String[] { "<name> <team> <type>" };
        this.description = "Creates an entity of the specified type.";
    }

    @Override
    public void onRun(CommandSender s, String[] args) {
        if (args.length != 3) {
            showUsageMessage(s);
            return;
        }

        Arena a = Editor.getCurrentArena();
        Player p = (Player) s;

        String entityName = args[0];
        int entityTeam = NumberUtils.toInt(args[1]);
        if (entityTeam > 2) {
            MessageManager.error(s, "Invalid team value: " + entityTeam + ". Must be 0 (global), 1 (Team A), or 2 (Team B).");
            return;
        }

        if (args[2].equalsIgnoreCase("SHOP_ENTITY")) {
            MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.ZOMBIE_SPAWN_EGG, 1), 99999);
            recipe.addIngredient(new ItemStack(Material.ROTTEN_FLESH, 4));
            recipe.addIngredient(a.getCoinItemStack(15));
            List<MerchantRecipe> recipeList = new ArrayList<>();
            recipeList.add(recipe);


            ShopEntity newEntity = new ShopEntity(entityName, p.getLocation(), EntityType.VILLAGER, recipeList);
            a.addEntity(entityTeam, newEntity);
            ArenaManager.save();
            MessageManager.info(s, "Created new shop entity at this location.");
        }
    }
}
