package com.mikarific.originaddons.mixin.ad;

import com.mikarific.originaddons.OriginAddons;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractInventoryScreen.class)
public abstract class PotionMixin<T extends ScreenHandler> extends HandledScreen<T> {
    public PotionMixin(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @ModifyArg(
            method = "drawStatusEffectDescriptions",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/font/TextRenderer.drawWithShadow (Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"
            ),
            index = 1
    )
    private Text changeMiningFatigueText(Text text) {
        if(text.getString().contains("ꑧ") && OriginAddons.onOriginRealms()) return Text.translatable("originaddons.ad");
        return text;
    }

    @Redirect(
            method = "drawStatusEffectSprites",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/gui/screen/ingame/AbstractInventoryScreen.drawSprite (Lnet/minecraft/client/util/math/MatrixStack;IIIIILnet/minecraft/client/texture/Sprite;)V"
            )
    )
    private void changeMiningFatigueSprite(MatrixStack matrices, int x, int y, int zOffset, int width, int height, Sprite sprite) {
        if (sprite.getAtlasId().toString().equals("minecraft:mob_effect/mining_fatigue") && OriginAddons.onOriginRealms()) {
            RenderSystem.setShaderTexture(0, new Identifier("originaddons", "gui/inventory/ad.png"));
            AbstractInventoryScreen.drawTexture(matrices, x, y, width, height, 0, 0, width, height, 18, 18);
        } else {
            AbstractInventoryScreen.drawSprite(matrices, x, y, zOffset, width, height, sprite);
        }
    }
}