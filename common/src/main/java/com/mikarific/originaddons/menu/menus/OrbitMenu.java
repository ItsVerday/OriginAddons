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
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;

public class OrbitMenu extends CustomMenu {
    public static final String TITLE = "숰";

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "textures/gui/custommenus/orbit.png");
        int TEXTURE_WIDTH = 316;
        int TEXTURE_HEIGHT = 112;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 112) / 2, 176, 112, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Profile
        addSelectableElement(new UIButton(TEXTURE, 98, 12, 70, 16, 176, 0, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(8);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(8).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Quests
        addSelectableElement(new UIButton(TEXTURE, 98, 30, 70, 16, 176, 32, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(17);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(17).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Friends
        addSelectableElement(new UIButton(TEXTURE, 98, 48, 70, 16, 176, 64, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(26);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(26).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Discord
        addSelectableElement(new UIButton(TEXTURE, 98, 66, 70, 16, 246, 0, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(35);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(35).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Settings
        addSelectableElement(new UIButton(TEXTURE, 98, 84, 70, 16, 246, 32, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(44);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(44).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
    }

    @Override
    protected void draw(Screen screen) {}

    @Override
    public void close(Screen screen) {}

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}