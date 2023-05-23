package com.mikarific.originaddons.mixin.join;

import com.google.gson.JsonObject;
import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.join.JoinScreen;
import com.mikarific.originaddons.join.UpdateScreen;
import com.mikarific.originaddons.util.Other;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin {
    @Inject(method = "connect(Lnet/minecraft/client/network/ServerInfo;)V", at = @At("HEAD"), cancellable = true)
    private void connect(ServerInfo entry, CallbackInfo ci) throws IOException {
        try {
            if (OriginAddons.onOriginRealms(entry.address)) {
                URL infoUrl = new URL("https://api.originaddons.com/info.json?v=" + Instant.now().toEpochMilli());
                InputStreamReader infoReader = new InputStreamReader(infoUrl.openStream());
                JsonObject info = JsonHelper.deserialize(infoReader).getAsJsonObject();
                if (Other.newerVersionExists(info) && OriginAddons.getConfig().updateNotifications) {
                    String latestVersion = info.get("latestVersion").getAsString();
                    MinecraftClient.getInstance().setScreen(new UpdateScreen((MultiplayerScreen) (Object) this, entry, info, latestVersion));
                } else {
                    MinecraftClient.getInstance().setScreen(new JoinScreen((MultiplayerScreen) (Object) this, entry, info));
                }

                ci.cancel();
            }
        } catch (UnknownHostException e) {
            OriginAddons.LOGGER.error("Failed to connect to api.originaddons.com! Connecting without updating assets...");
            e.printStackTrace();
        }
    }
}
