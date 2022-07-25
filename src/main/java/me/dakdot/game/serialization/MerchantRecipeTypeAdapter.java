package me.dakdot.game.serialization;

import com.google.gson.*;
import org.bukkit.inventory.MerchantRecipe;

import java.lang.reflect.Type;

public class MerchantRecipeTypeAdapter implements JsonSerializer<MerchantRecipe>, JsonDeserializer<MerchantRecipe> {
    @Override
    public MerchantRecipe deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, MerchantRecipe.class);
    }

    @Override
    public JsonElement serialize(MerchantRecipe merchantRecipe, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(merchantRecipe);
    }
}
