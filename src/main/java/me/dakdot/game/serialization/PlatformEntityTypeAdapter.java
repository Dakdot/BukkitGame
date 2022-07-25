package me.dakdot.game.serialization;

import com.google.gson.*;
import me.dakdot.game.entity.PlatformEntity;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class PlatformEntityTypeAdapter implements JsonSerializer<PlatformEntity>, JsonDeserializer<PlatformEntity> {
    @Override
    public PlatformEntity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject o = jsonElement.getAsJsonObject();
        Location location = jsonDeserializationContext.deserialize(o.get("location"), Location.class);
        Location spawnLocation = jsonDeserializationContext.deserialize(o.get("spawnLocation"), Location.class);

        return new PlatformEntity(o.get("name").getAsString(), location, spawnLocation);
    }

    @Override
    public JsonElement serialize(PlatformEntity e, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject res = new JsonObject();
        res.addProperty("type", e.getClass().getName());
        res.addProperty("name", e.getName());
        res.add("location", jsonSerializationContext.serialize(e.getLocation()));
        res.add("spawnLocation", jsonSerializationContext.serialize(e.getSpawnLocation()));
        return res;
    }
}
