package com.mikarific.originaddons.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
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
    private static boolean forcedTooltip = false;
    private static double forcedTooltipX = 0;
    private static double forcedTooltipY = 0;

    public static void setForcedTooltip(boolean forcedTooltip, double forcedTooltipX, double forcedTooltipY) {
        MenuUtils.forcedTooltip = forcedTooltip;
        MenuUtils.forcedTooltipX = forcedTooltipX;
        MenuUtils.forcedTooltipY = forcedTooltipY;
    }

    public static double getTooltipX(double currentTooltipX) {
        if (forcedTooltip) {
            return forcedTooltipX;
        }

        return currentTooltipX;
    }

    public static double getTooltipY(double currentTooltipY) {
        if (forcedTooltip) {
            return forcedTooltipY;
        }

        return currentTooltipY;
    }

    public static void sendCommand(ClientPlayerEntity player, String text) {
        if (text.startsWith("/")) {
            text = text.substring(1);
        }

        player.networkHandler.sendChatCommand(text);
    }

    public static List<Text> getDisplayTooltip(ItemStack item) {
        return item.getTooltip(MinecraftClient.getInstance().player, TooltipContext.Default.BASIC);
    }

    public static void renderTooltip(List<Text> tooltip, Screen screen, DrawContext context, double tx, double ty) {
        screen.setTooltip(tooltip.stream().map(Text::asOrderedText).toList());
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
