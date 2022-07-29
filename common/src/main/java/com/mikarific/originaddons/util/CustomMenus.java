package com.mikarific.originaddons.util;

import com.mikarific.originaddons.OriginAddons;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import java.util.Objects;

public class CustomMenus {
    private static boolean teleportingHome = false;

    public static boolean isNavigatorEnabled() {
        return OriginAddons.onOriginRealms() && OriginAddons.getConfig().customMenus && OriginAddons.getConfig().customMenusCategory.navigator;
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
            Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new ClickSlotC2SPacket(screenHandler.syncId, 0, slot, 0, SlotActionType.PICKUP, screenHandler.getSlot(0).getStack(), stack));
        }
    }
}
