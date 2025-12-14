package dev.codedsakura.blossom.back.data;

import com.google.gson.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import java.lang.reflect.Type;

public class ServerWorldSerializer implements JsonSerializer<ServerLevel>, JsonDeserializer<ServerLevel> {
    public static MinecraftServer server;

    @Override
    public ServerLevel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return server.getLevel(ResourceKey.create(Registries.DIMENSION, Identifier.parse(jsonElement.getAsString())));
    }

    @Override
    public JsonElement serialize(ServerLevel serverWorld, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(serverWorld.dimension().identifier().toString());
    }
}
