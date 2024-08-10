package com.mikarific.originaddons.util;

import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.menu.CustomMenus;
import com.mikarific.originaddons.ui.Window;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.*;

import javax.tools.Tool;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class MenuUtils {
    private static boolean forcedTooltip = false;
    private static double forcedTooltipX = 0;
    private static double forcedTooltipY = 0;

    public static Window window = null;

    public static void setCurrentMenu(CustomMenu menu, Screen screen) {
        CustomMenus.setCurrentMenu(menu);
        window = new Window();
        menu.doInit(screen, window);
    }

    public static void clearCurrentMenu(Screen screen) {
        if (CustomMenus.getCurrentMenu() != null) CustomMenus.getCurrentMenu().close(screen);
        CustomMenus.setCurrentMenu(null);
        window = null;
    }

    public static void initMenu(Screen screen) {
        CustomMenu menu = CustomMenus.getMenuForScreen(screen);
        if (menu == null) {
            clearCurrentMenu(screen);
            //List<Drawable> drawables = ScreenHandler.handleScreen(screen);
            //for (Drawable drawable: drawables) {
            //    addDrawable(drawable);
            //}

            return;
        }

        if (CustomMenus.getCurrentMenu() != null) {
            if (!CustomMenus.getCurrentMenu().equals(menu)) {
                clearCurrentMenu(screen);
                setCurrentMenu(menu, screen);
            } else {
                CustomMenus.getCurrentMenu().update(screen, window);
            }
        } else {
            clearCurrentMenu(screen);
            setCurrentMenu(menu, screen);
        }
    }

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
        return item.getTooltip(Item.TooltipContext.DEFAULT, MinecraftClient.getInstance().player, TooltipType.BASIC);
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
        if (content instanceof PlainTextContent.Literal) {
            return new PlainTextContent.Literal(transformer.apply(((PlainTextContent.Literal) content).string()));
        }

        return content;
    }
}
