package com.mikarific.originaddons.fabric;

import com.mikarific.originaddons.OriginAddons;
import net.fabricmc.api.ModInitializer;

public class OriginAddonsFabric implements ModInitializer {
    @Override
    public void onInitialize() { OriginAddons.init(); }
}