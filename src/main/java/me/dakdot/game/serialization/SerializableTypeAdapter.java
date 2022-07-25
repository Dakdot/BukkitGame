package me.dakdot.game.serialization;

import com.google.gson.*;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class SerializableTypeAdapter implements JsonSerializer<ConfigurationSerializable> {
    @Override
    public JsonElement serialize(ConfigurationSerializable src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.serialize());
    }
}
