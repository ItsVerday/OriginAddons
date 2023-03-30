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

public class RealmSettingsMenu extends CustomMenu {
    public static final String TITLE = "솭";

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/realms_settings.png");
        int TEXTURE_WIDTH = 266;
        int TEXTURE_HEIGHT = 52;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 52) / 2 - 26, 176, 52, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Realm Members
        addSelectableElement(new UIButton(TEXTURE, 25, 17, 36, 18, 176, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(1);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(1).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Reset Realm
        new UIButton(TEXTURE, 79, 17, 18, 18, 212, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(4);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(4).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Visiting Rules
        addSelectableElement(new UIButton(TEXTURE, 115, 17, 36, 18, 230, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(6);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(6).getStack()), (int)x, (int)y);
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