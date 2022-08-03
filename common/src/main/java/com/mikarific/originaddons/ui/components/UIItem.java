package com.mikarific.originaddons.ui.components;

import com.mikarific.originaddons.ui.Window;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class UIItem extends UIButton {
    private ItemStack stack;
    private final int innerX;
    private final int innerY;
    private final boolean drawHighlight;

    public UIItem(ItemStack stack, int innerX, int innerY, boolean drawHighlight, Identifier identifier, int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, Runnable action, boolean playSound) {
        super(identifier, x, y, width, height, u, v, hoveredVOffset, textureWidth, textureHeight, action, playSound);
        this.stack = stack;
        this.innerX = innerX;
        this.innerY = innerY;
        this.drawHighlight = drawHighlight;
    }

    public UIItem(ItemStack stack, int innerX, int innerY, boolean drawHighlight, Identifier identifier, int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, Runnable action, TooltipSupplier tooltipSupplier, boolean playSound) {
        super(identifier, x, y, width, height, u, v, hoveredVOffset, textureWidth, textureHeight, action, tooltipSupplier, playSound);
        this.stack = stack;
        this.innerX = innerX;
        this.innerY = innerY;
        this.drawHighlight = drawHighlight;
    }

    public void draw(@NotNull MatrixStack matricies, double mouseX, double mouseY) {
        if (this.isVisible() && stack != null && !stack.getTranslationKey().equals("block.minecraft.air")) {
            super.draw(matricies, mouseX, mouseY);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.enableDepthTest();
            MinecraftClient.getInstance().getItemRenderer().renderInGuiWithOverrides(MinecraftClient.getInstance().player, stack, (int) (this.getX() + this.innerX), (int) (this.getY() + this.innerY), (int) (this.getX() + this.innerX + (this.getY() + this.innerY) * MinecraftClient.getInstance().getWindow().getWidth()));
        }
        if (this.isVisible() && this.isHovered() && drawHighlight) {
            RenderSystem.disableDepthTest();
            RenderSystem.colorMask(true, true, true, false);
            DrawableHelper.fill(matricies, (int) this.getX(), (int) this.getY(), (int) (this.getX() + this.getWidth()), (int) (this.getY() + this.getHeight()), -2130706433);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.enableDepthTest();
        }
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public UIItem setStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }
}