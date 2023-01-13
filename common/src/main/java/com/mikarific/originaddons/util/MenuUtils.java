package com.mikarific.originaddons.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.*;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

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

    public static Text transformStringsInText(Text original, Function<String, String> transformer) {
        MutableText transformed = MutableText.of(transformTextContent(original.getContent(), transformer));
        transformed.setStyle(original.getStyle());
        for (Text child: original.getSiblings()) {
            transformed.append(transformStringsInText(child, transformer));
        }

        return transformed;
    }

    private static TextContent transformTextContent(TextContent content, Function<String, String> transformer) {
        if (content instanceof LiteralTextContent) {
            return new LiteralTextContent(transformer.apply(((LiteralTextContent) content).string()));
        }

        return content;
    }
}
