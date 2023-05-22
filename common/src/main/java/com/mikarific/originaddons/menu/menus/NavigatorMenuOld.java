package com.mikarific.originaddons.menu.menus;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;

public class NavigatorMenuOld extends CustomMenu {
    public static final String TITLE = "섦";

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ScreenHandler screenHandler = player.currentScreenHandler;

        Identifier MAIN_TEXTURE = new Identifier("originaddons", "gui/custommenus/navigator.png");
        int MAIN_TEXTURE_WIDTH = 404;
        int MAIN_TEXTURE_HEIGHT = 141;
        UIComponent box = new UITexture(MAIN_TEXTURE, (screen.width - 176) / 2, (screen.height - 141) / 2, 176, 141, 0, 0, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT).setChildOf(window);
        //Spawn
        addSelectableElement(new UIButton(MAIN_TEXTURE, 15, 41, 38, 38, 176, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(0);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(0).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Realms
        addSelectableElement(new UIButton(MAIN_TEXTURE, 69, 41, 38, 38, 214, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(3);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(3).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Resource Worlds
        addSelectableElement(new UIButton(MAIN_TEXTURE, 123, 41, 38, 38, 252, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(6);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(6).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Homes
        addSelectableElement(new UIButton(MAIN_TEXTURE, 15, 95, 38, 38, 290, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(27);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(27).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Towns
        new UIButton(MAIN_TEXTURE, 69, 95, 38, 38, 328, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(30);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(30).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Adventure Worlds
        new UIButton(MAIN_TEXTURE, 123, 95, 38, 38, 366, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(33);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(33).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);

        //Teleport Home
        addSelectableElement(new UIButton(MAIN_TEXTURE, 76, 79, 24, 12, 291, 97, 12, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/realm tp");
            player.closeHandledScreen();
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.teleporthome.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.teleporthome.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, false).setChildOf(box));

        //Auction House
        addSelectableElement(new UIButton(MAIN_TEXTURE, 15, 17, 38, 19, 176, 96, 19, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/ah");
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.auctionhouse.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.auctionhouse.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, true).setChildOf(box));
        //Streak
        addSelectableElement(new UIButton(MAIN_TEXTURE, 69, 17, 38, 19, 214, 96, 19, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/s");
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.streak.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.streak.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, true).setChildOf(box));
        //Gestures
        new UIButton(MAIN_TEXTURE, 123, 17, 38, 19, 252, 96, 19, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/g");
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.gestures.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.gestures.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, true).setChildOf(box);

        //Profile
        new UIButton(MAIN_TEXTURE, 15, 3, 24, 10, 176, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/profile");
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.profile.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.profile.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, true).setChildOf(box);
        //Quests
        new UIButton(MAIN_TEXTURE, 39, 3, 25, 10, 200, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/quests");
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.quests.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.quests.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, true).setChildOf(box);
        //Friends
        new UIButton(MAIN_TEXTURE, 64, 3, 24, 10, 225, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/friends");
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.friends.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.friends.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, true).setChildOf(box);
        //Discord
        new UIButton(MAIN_TEXTURE, 88, 3, 24, 10, 249, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            player.closeHandledScreen();
            MinecraftClient.getInstance().setScreen(new ConfirmLinkScreen((confirmed) -> {
                if (confirmed) {
                    Util.getOperatingSystem().open("https://discord.gg/MHRhtddvRW");
                }
                MinecraftClient.getInstance().setScreen(screen);
            }, "https://discord.gg/MHRhtddvRW", true));
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.discord.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.discord.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, true).setChildOf(box);
        //Settings
        new UIButton(MAIN_TEXTURE, 112, 3, 25, 10, 273, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/settings");
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.settings.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.settings.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, true).setChildOf(box);
        //Wiki
        new UIButton(MAIN_TEXTURE, 137, 3, 24, 10, 298, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
            player.closeHandledScreen();
            MinecraftClient.getInstance().setScreen(new ConfirmLinkScreen((confirmed) -> {
                if (confirmed) {
                    Util.getOperatingSystem().open("https://originrealms.wiki/");
                }
                MinecraftClient.getInstance().setScreen(screen);
            }, "https://originrealms.wiki/", true));
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, List.of(Text.translatable("originaddons.menus.navigator.wiki.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), Text.translatable("originaddons.menus.navigator.wiki.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
        }, true).setChildOf(box);
    }

    @Override
    protected void draw(Screen screen) {}

    @Override
    public void close(Screen screen) {}

    @Override
    public boolean isEnabled() {
        return OriginAddons.getConfig().customMenus;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}