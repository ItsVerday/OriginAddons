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
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;

public class NavigatorBalloonsMenuOld extends CustomMenu {
    public static final String TITLE = "솝";

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ScreenHandler screenHandler = player.currentScreenHandler;

        Identifier BALLOON_TEXTURE = new Identifier("originaddons", "gui/custommenus/navigator_balloon.png");
        int BALLOON_TEXTURE_WIDTH = 306;
        int BALLOON_TEXTURE_HEIGHT = 152;
        UIComponent box = new UITexture(BALLOON_TEXTURE, (screen.width - 176) / 2, (screen.height - 124) / 2, 176, 124, 0, 0, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT).setChildOf(window);
        //Red Balloon
        addSelectableElement(new UIButton(BALLOON_TEXTURE, 15, 16, 65, 38, 176, 0, 38, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(0);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(0).getStack()), (int) x, (int) y);
        }, false).setChildOf(box));
        //Yellow Balloon
        addSelectableElement(new UIButton(BALLOON_TEXTURE, 96, 16, 65, 38, 241, 0, 38, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(5);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(5).getStack()), (int) x, (int) y);
        }, false).setChildOf(box));
        addSelectableElement(new UIButton(BALLOON_TEXTURE, 15, 70, 65, 38, 176, 76, 38, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(27);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(27).getStack()), (int) x, (int) y);
        }, false).setChildOf(box));
        addSelectableElement(new UIButton(BALLOON_TEXTURE, 96, 70, 65, 38, 241, 76, 38, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(32);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(32).getStack()), (int) x, (int) y);
        }, false).setChildOf(box));
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