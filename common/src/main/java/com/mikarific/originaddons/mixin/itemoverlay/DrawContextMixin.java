package com.mikarific.originaddons.mixin.itemoverlay;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.ItemStackUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public class DrawContextMixin {
    @Inject(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V", at = @At("RETURN"))
    private void drawItemOverlay(LivingEntity entity, World world, ItemStack stack, int x, int y, int seed, int z, CallbackInfo ci) {
        if (!OriginAddons.onOriginRealms()) return;

        Identifier overlayTextureIdentifier = ItemStackUtils.getItemOverlayIdentifier(stack);
        if (overlayTextureIdentifier == null) {
            return;
        }

        DrawContext self = (DrawContext) (Object) this;

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        self.drawTexture(overlayTextureIdentifier, x, y, z + 250, 0, 0, 16, 16, 16, 16);

        RenderSystem.defaultBlendFunc();
    }
}
