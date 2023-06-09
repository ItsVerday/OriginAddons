package com.mikarific.originaddons.mixin.rocketboots;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.ItemStackUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    private static final Identifier ICONS = new Identifier("originaddons", "textures/gui/rocket_boots_fuel_bar.png");

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow @Final private Random random;

    @Shadow protected abstract int getHeartRows(int heartCount);

    @Shadow protected abstract int getHeartCount(LivingEntity entity);

    private static int getMaxFuel(ItemStack itemStack) {
        return ItemStackUtils.getMaximumRocketBootsFuel(ItemStackUtils.getItemStackCustomID(itemStack));
    }

    private static int getCurrentFuel(ItemStack itemStack) {
        List<Text> tooltip = itemStack.getTooltip(MinecraftClient.getInstance().player, TooltipContext.Default.NORMAL);

        for (Text text: tooltip) {
            String toString = text.getString();

            if (toString.contains("Fuel: ")) {
                return Integer.parseInt(toString.substring("Fuel: ".length()));
            }
        }

        return 0;
    }

    private boolean shouldRenderRocketBootsFuelBar() {
        if (!OriginAddons.onOriginRealms()) return false;
        if (!OriginAddons.getConfig().rocketBootsFuelBar) return false;

        PlayerEntity playerEntity = getCameraPlayer();
        if (playerEntity == null) return false;

        Identifier worldKey = playerEntity.world.getRegistryKey().getValue();
        if (!OriginAddons.getConfig().neverHideRocketBootsFuelBar && worldKey.getNamespace().equals("minecraft") && (worldKey.getPath().equals("overworld") || worldKey.getPath().equals("the_nether"))) return false;

        ItemStack boots = playerEntity.getEquippedStack(EquipmentSlot.FEET);
        if (boots.isEmpty()) return false;

        int maxFuel = getMaxFuel(boots);
        if (maxFuel <= 0) return false;

        return true;
    }

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMaxAir()I", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderRocketBootsFuelBar(MatrixStack matrices, CallbackInfo ci, PlayerEntity playerEntity, int i, boolean bl, long l, int j, HungerManager hungerManager, int k, int m, int n, int o, float f, int p, int q, int r, int s, int t, int u, int v, LivingEntity livingEntity, int x) {
        if (!OriginAddons.onOriginRealms()) return;
        if (!OriginAddons.getConfig().rocketBootsFuelBar) return;
        if (playerEntity == null) return;

        Identifier worldKey = playerEntity.world.getRegistryKey().getValue();
        if (!OriginAddons.getConfig().neverHideRocketBootsFuelBar && worldKey.getNamespace().equals("minecraft") && (worldKey.getPath().equals("overworld") || worldKey.getPath().equals("the_nether"))) return;

        ItemStack boots = playerEntity.getEquippedStack(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        int maxFuel = getMaxFuel(boots);
        if (maxFuel <= 0) return;

        int currentFuel = getCurrentFuel(boots);
        if (!playerEntity.getAbilities().allowFlying && currentFuel == 1) currentFuel = 0;

        if (!shouldRenderRocketBootsFuelBar()) return;

        int y = t + 10;

        int maxAir = playerEntity.getMaxAir();
        int currentAir = Math.min(playerEntity.getAir(), maxAir);
        if (playerEntity.isSubmergedIn(FluidTags.WATER) || currentAir < maxAir) {
            int delta = getHeartRows(getHeartCount(playerEntity));
            y -= delta * 10;
        }

        RenderSystem.setShaderTexture(0, ICONS);
        for (int w = 0; w < 10; ++w) {
            int thisFuel = w * 3;
            int rocketX = n - (9 - w) * 8 - 9;

            int textureU = 0;
            if (currentFuel == thisFuel + 1) {
                textureU = 9;
            } else if (currentFuel == thisFuel + 2) {
                textureU = 18;
            } else if (currentFuel >= thisFuel + 3) {
                textureU = 27;
            }

            int jitter = 0;
            if (currentFuel <= 2 && currentFuel > 0 && !OriginAddons.getConfig().noRocketBootsFuelBarShaking) jitter = random.nextInt(2);

            DrawableHelper.drawTexture(matrices, rocketX, y + jitter, textureU, 0, 9, 9, 36, 9);
        }

        RenderSystem.setShaderTexture(0, InGameHud.GUI_ICONS_TEXTURE);
    }

    @ModifyConstant(method = "render", constant = @Constant(floatValue = -4.0f))
    private float moveActionBar(float constant) {
        if (shouldRenderRocketBootsFuelBar()) {
            return constant - 10;
        }

        return constant;
    }
}