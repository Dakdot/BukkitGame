package me.dakdot.game.serialization;

import com.google.gson.*;
import me.dakdot.game.entity.UpgradeEntity;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class UpgradeEntityTypeAdapter implements JsonSerializer<UpgradeEntity>, JsonDeserializer<UpgradeEntity> {
    @Override
    public UpgradeEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject o = json.getAsJsonObject();
        return new UpgradeEntity(o.get("name").getAsString(),
                context.deserialize(o.get("upgrade_sign_location"), Location.class),
                context.deserialize(o.get("light_location"), Location.class),
                o.get("entity_upgrading").getAsString(),
                1
        );
    }

    @Override
    public JsonElement serialize(UpgradeEntity src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject res = new JsonObject();
        res.addProperty("type", src.getClass().getName());
        res.addProperty("name", src.getName());
        res.add("upgrade_sign_location", context.serialize(src.getUpgradeSignLocation()));
        res.add("light_location", context.serialize(src.getIndicatorLightLocation()));
        res.add("entity_upgrading", context.serialize(src.getGeneratorEntity().getName()));
        return res;
    }
}
