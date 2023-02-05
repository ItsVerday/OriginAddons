package com.mikarific.originaddons.forge;

import com.mikarific.originaddons.OriginAddons;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod(OriginAddons.MOD_ID)
public class OriginAddonsForge {
    public OriginAddonsForge() {
        OriginAddons.init(getModVersion());
    }

    public static String getModVersion() {
        if (ModList.get().getModContainerById("originaddons").isPresent()) {
            return ModList.get().getModContainerById("originaddons").get().getModInfo().getVersion().toString();
        } else {
            return "";
        }
    }
}