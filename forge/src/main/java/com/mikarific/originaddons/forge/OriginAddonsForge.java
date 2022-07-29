package com.mikarific.originaddons.forge;

import com.mikarific.originaddons.OriginAddons;
import net.minecraftforge.fml.common.Mod;

@Mod(OriginAddons.MOD_ID)
public class OriginAddonsForge {
    public OriginAddonsForge() {
        OriginAddons.init();
    }
}