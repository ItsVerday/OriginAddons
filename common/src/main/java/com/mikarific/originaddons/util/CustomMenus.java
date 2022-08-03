package com.mikarific.originaddons.util;

import com.mikarific.originaddons.OriginAddons;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import java.util.Objects;

public class CustomMenus {
    private static boolean teleportingHome = false;

    public static boolean isNavigatorEnabled() {
        return OriginAddons.onOriginRealms() && OriginAddons.getConfig().customMenus && OriginAddons.getConfig().customNavigatorMenu;
    }

    public static boolean isOrbitEnabled() {
        return OriginAddons.onOriginRealms() && OriginAddons.getConfig().customMenus && OriginAddons.getConfig().customOrbitMenu;
    }

    public static boolean isProfileEnabled() {
        return OriginAddons.onOriginRealms() && OriginAddons.getConfig().customMenus && OriginAddons.getConfig().customProfileMenu;
    }

    public static void setTeleportingHome(boolean teleportingHome) {
        CustomMenus.teleportingHome = teleportingHome;
    }
    public static boolean getTeleportingHome() {
        return teleportingHome;
    }

    public static void pickupItemAtSlot(int slot) {
        if (MinecraftClient.getInstance().player.currentScreenHandler instanceof GenericContainerScreenHandler screenHandler) {
            Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
            stack.put(slot, screenHandler.getSlot(slot).getStack());
            boolean hasShiftDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
            if (hasShiftDown) {
                Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new ClickSlotC2SPacket(screenHandler.syncId, 0, slot, 0, SlotActionType.QUICK_MOVE, screenHandler.getSlot(0).getStack(), stack));
            } else {
                Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new ClickSlotC2SPacket(screenHandler.syncId, 0, slot, 0, SlotActionType.PICKUP, screenHandler.getSlot(0).getStack(), stack));
            }
        }
    }
}
