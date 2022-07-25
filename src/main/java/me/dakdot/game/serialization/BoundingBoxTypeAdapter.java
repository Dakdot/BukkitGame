package me.dakdot.game.serialization;

import com.google.gson.*;
import org.bukkit.util.BoundingBox;

import java.lang.reflect.Type;
import java.util.Map;

public class BoundingBoxTypeAdapter implements JsonDeserializer<BoundingBox> {
    @Override
    public BoundingBox deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return BoundingBox.deserialize(context.deserialize(json, Map.class));
    }
}
