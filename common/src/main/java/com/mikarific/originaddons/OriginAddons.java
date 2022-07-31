package com.mikarific.originaddons;

import com.mikarific.originaddons.settings.SettingsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OriginAddons {
    public static final String MOD_ID = "originaddons";
    public static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        AutoConfig.register(SettingsConfig.class, GsonConfigSerializer::new);
    }

    public static SettingsConfig getConfig() {
        return AutoConfig.getConfigHolder(SettingsConfig.class).getConfig();
    }

    public static Screen getConfigScreen(Screen parent) {
        return AutoConfig.getConfigScreen(SettingsConfig.class, parent).get();
    }

    public static void debugLog(String message, boolean enabled) {
        if (enabled) LOGGER.info(message);
    }

    public static boolean onOriginRealms() {
        ServerInfo serverInfo = MinecraftClient.getInstance().getCurrentServerEntry();
        if (serverInfo == null) return false;

        String address = serverInfo.address.toLowerCase();
        if (address.endsWith(":25565")) address = address.substring(0, address.length() - ":25565".length());

        return address.endsWith("originrealms.com");
    }
}