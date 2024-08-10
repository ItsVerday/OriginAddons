package com.mikarific.originaddons.menu.menus;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class NavigatorMenu extends CustomMenu {
    public static final String TITLE = "슣";
    public static final String TITLE_NO_REALM = "스";
    public static final Identifier TEXTURE = new Identifier("originaddons", "textures/gui/custommenus/navigator.png");

    public static final int TEXTURE_WIDTH = 226;
    public static final int TEXTURE_HEIGHT = 332;


    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ScreenHandler screenHandler = player.currentScreenHandler;

        int MENU_WIDTH = 226;
        int MENU_HEIGHT = 124;
        UIComponent box = new UITexture(TEXTURE, (screen.width - MENU_WIDTH) / 2, (screen.height - MENU_HEIGHT) / 2, MENU_WIDTH, MENU_HEIGHT, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);

        Style tooltipTitle = Style.EMPTY.withColor(TextColor.parse("gold").getOrThrow()).withBold(true);
        Style tooltipDescription = Style.EMPTY.withColor(TextColor.parse("gray").getOrThrow());
        Style tooltipAction = Style.EMPTY.withColor(TextColor.parse("#85CC16").getOrThrow());
        Style tooltipAlternateAction = Style.EMPTY.withColor(TextColor.parse("#0EA6E9").getOrThrow());

        // Realms
        addButtonBaseAugmented(box, screen, screenHandler, player, 33, 9, 70, 52, 0, 124, 52, true, () -> {
            boolean hasShiftDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
            if (hasShiftDown) return "/realm tp";
            return null;
        }, 0,
                Text.translatable("originaddons.menus.navigator.realms.alt").setStyle(tooltipAlternateAction));
        // Spawn
        addButtonBaseAugmented(box, screen, screenHandler, player, 105, 9, 88, 34, 70, 124, 34, true, () -> {
            boolean hasShiftDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
            if (hasShiftDown) return "/spawn";
            return null;
        }, 4,
                Text.translatable("originaddons.menus.navigator.spawn.alt").setStyle(tooltipAlternateAction));
        // Open World
        addButtonBase(box, screen, screenHandler, player, 33, 63, 70,  52, 0,  228, 52, true, 27);
        // Homes
        addButtonBase(box, screen, screenHandler, player, 105, 45, 88, 34, 70, 192, 34, true, 22);
        // Towns
        addButtonBase(box, screen, screenHandler, player, 105, 81, 88, 34, 70, 260, 34, false, 40);

        // Auction House
        addButtonCustom(box, screen, player, 8, 9, 23, 70, 158, 124, 70, true, () -> "/ah",
                Text.translatable("originaddons.menus.navigator.auctionhouse.title").setStyle(tooltipTitle),
                Text.translatable("originaddons.menus.navigator.auctionhouse.description1").setStyle(tooltipDescription),
                Text.translatable("originaddons.menus.navigator.auctionhouse.description2").setStyle(tooltipDescription),
                Text.literal(""),
                Text.translatable("originaddons.menus.navigator.auctionhouse.action").setStyle(tooltipAction));
        // Friends / Party
        addButtonCustom(box, screen, player,  8, 81,  23, 34, 158, 264, 34, true, () -> {
            boolean hasShiftDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
            if (hasShiftDown) return "/party";
            return "/friends";
        }, Text.translatable("originaddons.menus.navigator.friends.title").setStyle(tooltipTitle),
                Text.translatable("originaddons.menus.navigator.friends.description1").setStyle(tooltipDescription),
                Text.translatable("originaddons.menus.navigator.friends.description2").setStyle(tooltipDescription),
                Text.literal(""),
                Text.translatable("originaddons.menus.navigator.friends.action").setStyle(tooltipAction),
                Text.translatable("originaddons.menus.navigator.friends.alt").setStyle(tooltipAlternateAction));
        // Gestures
        addButtonCustom(box, screen, player,  195, 9, 23, 52, 181, 124, 52, true, () -> "/g",
                Text.translatable("originaddons.menus.navigator.gestures.title").setStyle(tooltipTitle),
                Text.translatable("originaddons.menus.navigator.gestures.description").setStyle(tooltipDescription),
                Text.literal(""),
                Text.translatable("originaddons.menus.navigator.gestures.action").setStyle(tooltipAction));
        // Settings
        addButtonCustom(box, screen, player, 195, 63, 23,  34, 181, 228, 34, true, () -> "/settings",
                Text.translatable("originaddons.menus.navigator.settings.title").setStyle(tooltipTitle),
                Text.translatable("originaddons.menus.navigator.settings.description").setStyle(tooltipDescription),
                Text.literal(""),
                Text.translatable("originaddons.menus.navigator.settings.action").setStyle(tooltipAction)
        );
        // Streak
        addButtonCustom(box, screen, player, 195, 99, 23, 16, 181, 296, 16, true, () -> {
            boolean hasShiftDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
            if (hasShiftDown) return "/balloon green-balloon";
            return "/streaks";
        }, Text.translatable("originaddons.menus.navigator.streak.title").setStyle(tooltipTitle),
                Text.translatable("originaddons.menus.navigator.streak.description1").setStyle(tooltipDescription),
                Text.translatable("originaddons.menus.navigator.streak.description2").setStyle(tooltipDescription),
                Text.literal(""),
                Text.translatable("originaddons.menus.navigator.streak.action").setStyle(tooltipAction),
                Text.translatable("originaddons.menus.navigator.streak.alt").setStyle(tooltipAlternateAction));
    }

    private void addButtonBase(UIComponent box, Screen screen, ScreenHandler screenHandler, ClientPlayerEntity player, int x, int y, int width, int height, int u, int v, int hoveredVOffset, boolean selectable, int slot) {
        addButtonBaseAugmented(box, screen, screenHandler, player, x, y, width, height, u, v, hoveredVOffset, selectable, () -> null, slot);
    }

    private void addButtonBaseAugmented(UIComponent box, Screen screen, ScreenHandler screenHandler, ClientPlayerEntity player, int x, int y, int width, int height, int u, int v, int hoveredVOffset, boolean selectable, Supplier<String> possibleCommand, int slot, Text... extraTooltipLines) {
        addButton(box, x, y, width, height, u, v, hoveredVOffset, selectable, false, () -> {
            String command = possibleCommand.get();
            if (command != null) {
                MenuUtils.sendCommand(player, command);
            } else {
                MenuUtils.pickupItemAtSlot(slot);
            }
        }, (b, m, tx, ty) -> {
            List<Text> tooltip = MenuUtils.getDisplayTooltip(screenHandler.getSlot(slot).getStack());
            tooltip.addAll(List.of(extraTooltipLines));
            MenuUtils.renderTooltip(tooltip, screen, m, tx, ty);
        });
    }

    private void addButtonCustom(UIComponent box, Screen screen, ClientPlayerEntity player, int x, int y, int width, int height, int u, int v, int hoveredVOffset, boolean selectable, Supplier<String> command, Text... tooltip) {
        addButton(box, x, y, width, height, u, v, hoveredVOffset, selectable, true, () -> {
            MenuUtils.sendCommand(player, command.get());
        }, (b, m, tx, ty) -> {
            MenuUtils.renderTooltip(List.of(tooltip), screen, m, tx, ty);
        });
    }

    private void addButton(UIComponent box, int x, int y, int width, int height, int u, int v, int hoveredVOffset, boolean selectable, boolean playSound, Runnable action, UIButton.TooltipSupplier tooltip) {
        UIComponent button = new UIButton(TEXTURE, x, y, width, height, u, v, hoveredVOffset, TEXTURE_WIDTH, TEXTURE_HEIGHT, action, tooltip, playSound).setChildOf(box);

        if (selectable) addSelectableElement(button);
    }

    @Override
    protected void draw(Screen screen) {
    }

    @Override
    public void close(Screen screen) {
    }

    @Override
    public boolean isEnabled() {
        return OriginAddons.getConfig().customMenus;
    }

    @Override
    protected boolean matches(Screen screen) {
        String title = screen.getTitle().getString();
        return title.contains(TITLE) || title.contains(TITLE_NO_REALM);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}