package me.dakdot.game.serialization;

import com.google.gson.*;
import me.dakdot.game.entity.CoinGeneratorEntity;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class CoinGeneratorEntityTypeAdapter implements JsonSerializer<CoinGeneratorEntity>, JsonDeserializer<CoinGeneratorEntity> {
    @Override
    public CoinGeneratorEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject o = json.getAsJsonObject();
        CoinGeneratorEntity e = new CoinGeneratorEntity(o.get("name").getAsString(),
                context.deserialize(o.get("location"), Location.class));
        return e;
    }

    @Override
    public JsonElement serialize(CoinGeneratorEntity src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject res = new JsonObject();
        res.addProperty("type", src.getClass().getName());
        res.addProperty("name", src.getName());
        res.add("location", context.serialize(src.getLocation()));
        return res;
    }
}
