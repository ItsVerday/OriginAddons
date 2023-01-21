package com.mikarific.originaddons.mixin.cropstars;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.ItemStackUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "renderGuiItemModel", at = @At("RETURN"))
    private void renderCropStarOverlay(ItemStack stack, int x, int y, BakedModel model, CallbackInfo ci) {
        if (!OriginAddons.onOriginRealms()) return;

        Identifier overlayTextureIdentifier = ItemStackUtils.getItemOverlayIdentifier(stack);
        if (overlayTextureIdentifier == null) {
            return;
        }

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, overlayTextureIdentifier);
        DrawableHelper.drawTexture(new MatrixStack(), x, y, 0, 0, 16, 16, 16, 16);

        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
    }
}