package me.dakdot.game.serialization;

import com.google.gson.*;
import me.dakdot.game.entity.GeneratorEntity;
import org.bukkit.Location;
import org.bukkit.Material;

import java.lang.reflect.Type;

public class GeneratorEntityTypeAdapter implements JsonSerializer<GeneratorEntity>, JsonDeserializer<GeneratorEntity> {

    @Override
    public GeneratorEntity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject o = jsonElement.getAsJsonObject();
        Location location = jsonDeserializationContext.deserialize(o.get("location"), Location.class);
        Location spawnLocation = jsonDeserializationContext.deserialize(o.get("spawnLocation"), Location.class);
        Material material = jsonDeserializationContext.deserialize(o.get("material"), Material.class);

        GeneratorEntity e = new GeneratorEntity(o.get("name").getAsString(), location, spawnLocation, material);
        return e;
    }

    @Override
    public JsonElement serialize(GeneratorEntity e, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject res = new JsonObject();
        res.addProperty("type", e.getClass().getName());
        res.addProperty("name", e.getName());
        res.add("location", jsonSerializationContext.serialize(e.getLocation()));
        res.add("spawnLocation", jsonSerializationContext.serialize(e.getSpawnLocation()));
        res.add("material", jsonSerializationContext.serialize(e.getMaterial()));
        return res;
    }

}
