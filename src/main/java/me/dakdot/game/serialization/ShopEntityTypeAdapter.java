package me.dakdot.game.serialization;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import me.dakdot.game.entity.ShopEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShopEntityTypeAdapter implements JsonSerializer<ShopEntity>, JsonDeserializer<ShopEntity> {

    @Override
    public ShopEntity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject o = jsonElement.getAsJsonObject();
        Location location = jsonDeserializationContext.deserialize(o.get("location"), Location.class);
        EntityType entityType = jsonDeserializationContext.deserialize(o.get("entityType"), EntityType.class);
        Type listOfMerchantRecipeObject = new TypeToken<ArrayList<MerchantRecipe>>() {}.getType();
        Gson gson;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(ItemStack.class, new ItemStackTypeAdapter());
        gson = builder.create();
        List<MerchantRecipe> selling = gson.fromJson(o.get("selling"), listOfMerchantRecipeObject);

        return new ShopEntity(o.getAsString(), location, entityType, selling);
    }

    @Override
    public JsonElement serialize(ShopEntity e, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject res = new JsonObject();
        //res.addProperty("name", e.getName());
        res.addProperty("type", e.getClass().getName());
        res.add("location", jsonSerializationContext.serialize(e.getLocation()));
        res.add("entityType", jsonSerializationContext.serialize(e.getEntityType()));
        res.add("selling", jsonSerializationContext.serialize(e.getSelling()));
        return res;
    }
}
