package com.mikarific.originaddons.mixin.emojipicker;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.emojipicker.EmojiInstance;
import com.mikarific.originaddons.util.emojipicker.EmojiPicker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.encryption.SignatureVerifier;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onPlayerList", at = @At("HEAD"))
    private void handleMessaging(PlayerListS2CPacket packet, CallbackInfo ci) {
        if (!OriginAddons.onOriginRealms()) return;
        NetworkThreadUtils.forceMainThread(packet, ((ClientPlayNetworkHandler) (Object) this), MinecraftClient.getInstance());

        for (PlayerListS2CPacket.Action action: packet.getActions()) {
            if (action.equals(PlayerListS2CPacket.Action.ADD_PLAYER)) {
                for (PlayerListS2CPacket.Entry entry: packet.getEntries()) handlePlayerListAddition(entry);
            }
        }
    }

    private void handlePlayerListAddition(PlayerListS2CPacket.Entry entry) {
        String token = entry.profile().getName();
        if (!token.startsWith(":")) return;

        for (EmojiInstance emoji: EmojiPicker.getEmojis()) {
            if (emoji.getInfo().getToken().equals(token)) {
                emoji.setUnlocked(true);
                return;
            }
        }

        OriginAddons.LOGGER.warn("Unrecognized emoji " + token + " sent from server!");
    }
}