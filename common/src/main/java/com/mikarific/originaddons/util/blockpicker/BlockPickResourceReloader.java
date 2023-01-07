package com.mikarific.originaddons.util.blockpicker;

import com.google.gson.JsonObject;
import com.mikarific.originaddons.OriginAddons;
import net.minecraft.block.Block;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class BlockPickResourceReloader implements SynchronousResourceReloader {
    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, Resource> resources = manager.findResources("blockstates" , path -> path.toString().endsWith(".json"));
        for (Identifier resourceIdentifier: resources.keySet()) {
            try (InputStream stream = resources.get(resourceIdentifier).getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                JsonObject json = JsonHelper.deserialize(reader).getAsJsonObject();
                Identifier blockIdentifier = new Identifier(resourceIdentifier.getNamespace(), resourceIdentifier.getPath().replace("blockstates/", "").replace(".json", ""));
                BlockPicker.processBlockStateFile(json, blockIdentifier);
            } catch (Exception e) {
                OriginAddons.LOGGER.error("Error occurred processing blockstate file '" + resourceIdentifier + "'", e);
            }
        }
    }
}
