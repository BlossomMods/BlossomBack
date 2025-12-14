package dev.codedsakura.blossom.back.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.codedsakura.blossom.back.BlossomBack;
import dev.codedsakura.blossom.lib.data.DataController;
import dev.codedsakura.blossom.lib.teleport.TeleportUtils;
import net.minecraft.server.level.ServerLevel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDeathData extends DataController<HashMap<UUID, TeleportUtils.TeleportDestination>> {
    private final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ServerLevel.class, new ServerWorldSerializer())
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    @Override
    public HashMap<UUID, TeleportUtils.TeleportDestination> defaultData() {
        return new HashMap<>();
    }

    @Override
    public String getFilename() {
        return "BlossomBack.deaths";
    }

    public void updateData() {
        data.putAll(BlossomBack.DEATHS);
        this.write();
    }

    public TeleportUtils.TeleportDestination get(UUID player) {
        if (data.containsKey(player)) {
            return data.get(player);
        }
        return BlossomBack.DEATHS.get(player);
    }

    @Override
    protected HashMap<UUID, TeleportUtils.TeleportDestination> readJson(InputStreamReader reader) {
        return GSON.fromJson(new BufferedReader(reader), getType());
    }

    @Override
    protected void writeJson(OutputStreamWriter writer) {
        GSON.toJson(data, writer);
    }
}
