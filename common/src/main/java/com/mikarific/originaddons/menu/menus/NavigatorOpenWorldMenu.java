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

public class NavigatorOpenWorldMenu extends CustomMenu {
    public static final String TITLE = "꣓";
    public static final Identifier TEXTURE = new Identifier("originaddons", "textures/gui/custommenus/navigator_open_world.png");

    public static final int TEXTURE_WIDTH = 208;
    public static final int TEXTURE_HEIGHT = 302;

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ScreenHandler screenHandler = player.currentScreenHandler;

        int MENU_WIDTH = 176;
        int MENU_HEIGHT = 126;
        UIComponent box = new UITexture(TEXTURE, (screen.width - MENU_WIDTH) / 2, (screen.height - MENU_HEIGHT) / 2, MENU_WIDTH, MENU_HEIGHT, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);

        // Red
        addWorldButton(box, screen, screenHandler, 8, 26, 52, 44, 0, 126, 44, 9, "꣔", "꣚", 56);
        // Orange
        addWorldButton(box, screen, screenHandler, 62, 26, 52, 44, 52, 126, 44, 13, "꣕", "꣛", 66);
        // Yellow
        addWorldButton(box, screen, screenHandler,  116, 26, 52, 44, 104, 126, 44, 17, "꣖", "꣜", 76 );
        // Green
        addWorldButton(box, screen, screenHandler, 8, 72, 52,  44, 0, 214, 44,  36, "꣗", "꣝", 86);
        // Blue
        addWorldButton(box, screen, screenHandler, 62, 72, 52, 44, 52, 214, 44, 40, "꣘", "꣞", 96);
        // Purple
        addWorldButton(box, screen, screenHandler, 116, 72, 52, 44, 104, 214, 44, 44, "꣙", "꣟", 106);

        // Random Button
        addSelectableElement(new UIButton(TEXTURE, 134, 9, 16, 14, 192,  0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(7);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(7).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box));

        // Help Button
        addSelectableElement(new UIButton(TEXTURE, 152, 9, 16, 14, 192,  28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(8);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(8).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box));

        // Close button
        String backButton = "꣡";
        int closeV = 0;
        if (screen.getTitle().getString().contains(backButton)) {
            closeV = 28;
        }

        addSelectableElement(new UIButton(TEXTURE, 8, 9, 16, 14, 176, closeV, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(0);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(0).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box));
    }

    private void addWorldButton(UIComponent box, Screen screen, ScreenHandler screenHandler, int x, int y, int width, int height, int u, int v, int hoveredVOffset, int slot, String disabledChar, String graveChar, int graveV) {
        int finalU = u;
        int finalV = v;
        int finalHoveredVOffset = hoveredVOffset;
        boolean disabled = screen.getTitle().getString().contains(disabledChar);
        if (disabled) {
            finalU = 156;
            finalV = 126;
            finalHoveredVOffset = 0;
        }

        UIComponent button = new UIButton(TEXTURE, x, y, width, height, finalU, finalV, finalHoveredVOffset, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(slot);
        }, (b, m, tx, ty) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), screen, m, x, y);
        }, false).setChildOf(box);

        if (!disabled) {
            addSelectableElement(button);
        }

        if (screen.getTitle().getString().contains(graveChar)) {
            UIComponent grave = new UITexture(TEXTURE, 41, 30, 7, 10, 176, graveV, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(button);
        }
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
    public String getTitle() {
        return TITLE;
    }
}