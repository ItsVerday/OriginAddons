package com.mikarific.originaddons.fabric;

import com.mikarific.originaddons.OriginAddons;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class OriginAddonsFabric implements ModInitializer {
    @Override
    public void onInitialize() { OriginAddons.init(getModVersion()); }

    public static String getModVersion() {
        if (FabricLoader.getInstance().getModContainer("originaddons").isPresent()) {
            return FabricLoader.getInstance().getModContainer("originaddons").get().getMetadata().getVersion().getFriendlyString();
        } else {
            return "";
        }
    }
}