package com.mikarific.originaddons.ui.components;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class UIFace extends UIButton {
    private String username = null;
    private final int innerX;
    private final int innerY;
    private final int scale;

    public UIFace(String username, int innerX, int innerY, int scale, Identifier identifier, int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, Runnable action, boolean playSound) {
        super(identifier, x, y, width, height, u, v, hoveredVOffset, textureWidth, textureHeight, action, playSound);
        this.username = username;
        this.innerX = innerX;
        this.innerY = innerY;
        this.scale = scale;
    }

    public UIFace(String username, int innerX, int innerY, int scale, Identifier identifier, int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, Runnable action, TooltipSupplier tooltipSupplier, boolean playSound) {
        super(identifier, x, y, width, height, u, v, hoveredVOffset, textureWidth, textureHeight, action, tooltipSupplier, playSound);
        this.username = username;
        this.innerX = innerX;
        this.innerY = innerY;
        this.scale = scale;
    }

    public void draw(@NotNull DrawContext context, double mouseX, double mouseY, boolean hideTooltips) {
        super.draw(context, mouseX, mouseY, hideTooltips);
        if (this.isVisible()) {
            context.getMatrices().push();
            context.getMatrices().translate(this.getX(), this.getY(), 1f);
            Identifier texture;
            assert MinecraftClient.getInstance().player != null;
            GameProfile profile = null;
            List<String> onlinePlayers = MinecraftClient.getInstance().player.networkHandler.getPlayerList().stream().map(player -> player.getProfile().getName()).toList();
            if (onlinePlayers.contains(this.username)) {
                PlayerListEntry player = MinecraftClient.getInstance().player.networkHandler.getPlayerList().stream().toList().get(onlinePlayers.indexOf(this.username));
                profile = player.getProfile();
            } else if (this.username != null && !this.username.equals("")) {
                profile = new GameProfile(UUID.randomUUID(), this.username);
            }
            boolean skinProviderHasTexture = profile != null && MinecraftClient.getInstance().getSkinProvider().getTextures(profile).containsKey(MinecraftProfileTexture.Type.SKIN);
            if (skinProviderHasTexture) {
                MinecraftProfileTexture profileTexture = MinecraftClient.getInstance().getSkinProvider().getTextures(profile).get(MinecraftProfileTexture.Type.SKIN);
                texture = MinecraftClient.getInstance().getSkinProvider().loadSkin(profileTexture, MinecraftProfileTexture.Type.SKIN);
            } else {
                texture = profile != null ? DefaultSkinHelper.getTexture(profile.getId()) : DefaultSkinHelper.getTexture();
            }
            context.drawTexture(texture, innerX, innerY, 8 * scale, 8 * scale, 8, 8, 8, 8, 64, 64);
            context.drawTexture(texture, innerX - 1, innerY - 1, (8 * scale) + 2, (8 * scale) + 2, 40, 8, 8, 8, 64, 64);
            context.getMatrices().pop();
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
