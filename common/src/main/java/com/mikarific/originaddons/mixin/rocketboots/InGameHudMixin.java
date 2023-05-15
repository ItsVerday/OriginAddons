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
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    private static final Identifier ICONS = new Identifier("originaddons", "textures/gui/rocket_boots_fuel_bar.png");

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow @Final private Random random;

    private static int getMaxFuel(ItemStack itemStack) {
        switch (ItemStackUtils.getItemStackCustomID(itemStack)) {
            case "rocket_boots_90": return 90;
            case "rocket_boots_30": return 30;
            default: return 0;
        }
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

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;pop()V"))
    private void renderRocketBootsFuelBar(MatrixStack matrices, CallbackInfo ci) {
        if (!OriginAddons.onOriginRealms()) return;
        if (!OriginAddons.getConfig().rocketBootsFuelBar) return;

        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) return;

        Identifier worldKey = playerEntity.world.getRegistryKey().getValue();
        if (!OriginAddons.getConfig().neverHideRocketBootsFuelBar && worldKey.getNamespace().equals("minecraft") && (worldKey.getPath().equals("overworld") || worldKey.getPath().equals("the_nether"))) return;

        ItemStack boots = getCameraPlayer().getEquippedStack(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        int maxFuel = getMaxFuel(boots);
        if (maxFuel <= 0) return;

        int currentFuel = getCurrentFuel(boots);
        if (!playerEntity.getAbilities().allowFlying && currentFuel == 1) currentFuel = 0;

        int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        double maxHealth = playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
        int width = scaledWidth / 2 - 91;
        int absorption = MathHelper.ceil(playerEntity.getAbsorptionAmount());
        int rows = MathHelper.ceil((maxHealth + (float) absorption) / 2.0F / 10.0F);
        int heartsHeight = Math.max(10 - (rows - 2), 3);
        int height = scaledHeight - 39;
        int y = height - (rows - 1) * heartsHeight - 10;
        if (playerEntity.getArmor() > 0) y -= 10;

        RenderSystem.setShaderTexture(0, ICONS);
        for (int w = 0; w < 10; ++w) {
            int thisFuel = w * 3;
            int x = width + w * 8;

            int u = 0;
            if (currentFuel == thisFuel + 1) {
                u = 9;
            } else if (currentFuel == thisFuel + 2) {
                u = 18;
            } else if (currentFuel >= thisFuel + 3) {
                u = 27;
            }

            int jitter = 0;
            if (currentFuel <= 2 && currentFuel > 0 && !OriginAddons.getConfig().noRocketBootsFuelBarShaking) jitter = random.nextInt(2);

            DrawableHelper.drawTexture(matrices, x, y + jitter, u, 0, 9, 9, 36, 9);
        }
    }
}