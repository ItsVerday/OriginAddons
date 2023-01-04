package com.mikarific.originaddons.util.custommenus.screens;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.custommenus.CustomMenus;
import com.mikarific.originaddons.util.custommenus.CustomScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;

public class RealmsRoleSelect extends CustomScreen {
    public static void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/realms_role_select.png");
        int TEXTURE_WIDTH = 288;
        int TEXTURE_HEIGHT = 52;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 52) / 2, 176, 52, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Resident
        new UIButton(TEXTURE, 24, 17, 56, 18, 176, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(2);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(2).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Trusted
        new UIButton(TEXTURE, 98, 17, 56, 18, 232, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            CustomMenus.pickupItemAtSlot(6);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(6).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
    }
}
