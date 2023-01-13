package com.mikarific.originaddons.util;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;

public class MenuUtils {
    public static void sendMessage(ClientPlayerEntity player, String text) {
        player.sendChatMessage(text, Text.literal(text));
    }

    public static List<Text> getDisplayTooltip(ItemStack item) {
        return item.getTooltip(MinecraftClient.getInstance().player, TooltipContext.Default.NORMAL);
    }

    private static int clickButton = 0;

    public static void setClickButton(int clickButton) {
        MenuUtils.clickButton = clickButton;
    }

    public static void pickupItemAtSlot(int slot) {
        if (MinecraftClient.getInstance().player.currentScreenHandler instanceof GenericContainerScreenHandler screenHandler) {
            Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
            stack.put(slot, screenHandler.getSlot(slot).getStack());
            boolean hasShiftDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);

            SlotActionType actionType;
            if (hasShiftDown) {
                actionType = SlotActionType.QUICK_MOVE;
            } else {
                actionType = SlotActionType.PICKUP;
            }

            Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new ClickSlotC2SPacket(screenHandler.syncId, 0, slot, clickButton == 0 ? 0 : 1, actionType, screenHandler.getSlot(0).getStack(), stack));
        }
    }
}
