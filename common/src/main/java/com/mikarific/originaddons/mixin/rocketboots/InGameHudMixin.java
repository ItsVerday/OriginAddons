package com.mikarific.originaddons.mixin.rocketboots;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.ItemStackUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private static final Identifier RB_ICONS = new Identifier("originaddons", "textures/gui/rocket_boots_fuel_bar.png");
    @Unique
    private static final int FUEL_BAR_HEIGHT = 30;

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow @Final private Random random;

    @Shadow protected abstract int getHeartRows(int heartCount);

    @Shadow protected abstract int getHeartCount(LivingEntity entity);

    private static int getMaxFuel(ItemStack itemStack) {
        return ItemStackUtils.getMaximumRocketBootsFuel(ItemStackUtils.getItemStackCustomID(itemStack));
    }

    private static int getCurrentFuel(ItemStack itemStack) {
        List<Text> tooltip = itemStack.getTooltip(MinecraftClient.getInstance().player, TooltipContext.Default.BASIC);

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

        Identifier worldKey = playerEntity.getWorld().getRegistryKey().getValue();
        if (!OriginAddons.getConfig().neverHideRocketBootsFuelBar && worldKey.getNamespace().equals("minecraft") && (worldKey.getPath().equals("overworld") || worldKey.getPath().equals("the_nether"))) return false;

        ItemStack boots = playerEntity.getEquippedStack(EquipmentSlot.FEET);
        if (boots.isEmpty()) return false;

        int maxFuel = getMaxFuel(boots);
        if (maxFuel <= 0) return false;

        return true;
    }

    @Inject(method = "renderStatusBars", at = @At("HEAD"))
    private void renderRocketBootsFuelBar(DrawContext context, CallbackInfo ci) {
        if (!OriginAddons.onOriginRealms()) return;
        if (!OriginAddons.getConfig().rocketBootsFuelBar) return;

        PlayerEntity playerEntity = getCameraPlayer();
        if (playerEntity == null) return;

        Identifier worldKey = playerEntity.getWorld().getRegistryKey().getValue();
        if (!OriginAddons.getConfig().neverHideRocketBootsFuelBar && worldKey.getNamespace().equals("minecraft") && (worldKey.getPath().equals("overworld") || worldKey.getPath().equals("the_nether"))) return;

        ItemStack boots = playerEntity.getEquippedStack(EquipmentSlot.FEET);
        if (boots.isEmpty()) return;

        int maxFuel = getMaxFuel(boots);
        if (maxFuel <= 0) return;

        int currentFuel = getCurrentFuel(boots);
        if (!playerEntity.getAbilities().allowFlying && currentFuel == 1) currentFuel = 0;

        if (!shouldRenderRocketBootsFuelBar()) return;

        int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int barX = (scaledWidth / 2 - 5 / 2) + 96;
        int barY = scaledHeight - FUEL_BAR_HEIGHT - 4;

        if (currentFuel <= 2 && currentFuel > 0 && !OriginAddons.getConfig().noRocketBootsFuelBarShaking) {
            barX += random.nextInt(2) - 1;
            barY += random.nextInt(2);
        }

        int barHeight = currentFuel;
        if (barHeight < 0) barHeight = 0;
        if (barHeight > FUEL_BAR_HEIGHT) barHeight = FUEL_BAR_HEIGHT;

        context.drawTexture(RB_ICONS, barX, barY, 0, 0, 5, FUEL_BAR_HEIGHT + 1 - barHeight, 10, FUEL_BAR_HEIGHT + 2);
        context.drawTexture(RB_ICONS, barX, barY + FUEL_BAR_HEIGHT + 1 - barHeight, 5, FUEL_BAR_HEIGHT + 1 - barHeight, 5, 1 + barHeight, 10, FUEL_BAR_HEIGHT + 2);
    }
}