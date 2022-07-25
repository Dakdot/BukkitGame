package me.dakdot.game.serialization;

import com.google.gson.*;
import me.dakdot.game.Main;
import me.dakdot.game.entity.GameEntity;

import java.lang.reflect.Type;
import java.util.logging.Level;

public class GameEntityTypeAdapter implements JsonSerializer<GameEntity>, JsonDeserializer<GameEntity> {
    @Override
    public GameEntity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject o = jsonElement.getAsJsonObject();
        try {
            if (o.get("type") == null) {
                Main.plugin.getLogger().log(Level.SEVERE, "Could not parse arena. An entity does not have a type.");
                return null;
            }
            Class<?> c = Class.forName(o.get("type").getAsString());
            return jsonDeserializationContext.deserialize(jsonElement, c);
        } catch (ClassNotFoundException e) {
            Main.plugin.getLogger().log(Level.SEVERE, "Could not parse arena. An entity has an invalid type: " + o.get("type").getAsString());
            return null;
        }
    }

    @Override
    public JsonElement serialize(GameEntity gameEntity, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject o = jsonSerializationContext.serialize(gameEntity, gameEntity.getClass()).getAsJsonObject();
        o.addProperty("type", gameEntity.getClass().getName());
        return o;
    }
}
