package me.dakdot.game.serialization;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Map;

public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        /*Map map = jsonDeserializationContext.deserialize(jsonElement, Map.class);
        ItemStack res = ItemStack.deserialize(map);
        if (map.containsKey("displayName")) {
            res.getItemMeta().setDisplayName((String) map.get("displayName"));
        }*/
        return ItemStack.deserialize(jsonDeserializationContext.deserialize(jsonElement, Map.class));
    }

    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext jsonSerializationContext) {/*
        Map<String, Object> map = itemStack.serialize();
        System.out.println("Item" + itemStack.getItemMeta());
        if (!itemStack.getItemMeta().getDisplayName().equals(itemStack.getType().name())) {
            map.put("displayName", itemStack.getItemMeta().getDisplayName());
        }*/
        return jsonSerializationContext.serialize(itemStack.serialize());
    }
}
