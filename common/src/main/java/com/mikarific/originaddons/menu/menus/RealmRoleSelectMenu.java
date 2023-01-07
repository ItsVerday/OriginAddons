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

public class RealmRoleSelectMenu extends CustomMenu {
    public static final String TITLE = "솮";

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/realms_role_select.png");
        int TEXTURE_WIDTH = 288;
        int TEXTURE_HEIGHT = 52;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 52) / 2, 176, 52, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Resident
        addSelectableElement(new UIButton(TEXTURE, 24, 17, 56, 18, 176, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(2);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(2).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Trusted
        addSelectableElement(new UIButton(TEXTURE, 98, 17, 56, 18, 232, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
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
        return OriginAddons.getConfig().customRealmsMenu;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}