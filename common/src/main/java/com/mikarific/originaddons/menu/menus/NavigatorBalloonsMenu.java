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

public class NavigatorBalloonsMenu extends CustomMenu {
    public static final String TITLE = "슥";

    public static final Identifier TEXTURE = new Identifier("originaddons", "textures/gui/custommenus/navigator_balloon.png");

    public static final int TEXTURE_WIDTH = 192;
    public static final int TEXTURE_HEIGHT = 314;

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ScreenHandler screenHandler = player.currentScreenHandler;

        int MENU_WIDTH = 176;
        int MENU_HEIGHT = 110;
        UIComponent box = new UITexture(TEXTURE, (screen.width - MENU_WIDTH) / 2, (screen.height - MENU_HEIGHT) / 2, MENU_WIDTH, MENU_HEIGHT, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);

        // Red Balloon
        addBalloonButton(box, screen, screenHandler, 8, 26, 79, 34, 0, 110, 34, 9, null, 110);
        // Yellow Balloon
        addBalloonButton(box, screen, screenHandler, 89, 26, 79, 34, 79,  110, 34, 17, "슧", 178);
        // Blue Balloon
        addBalloonButton(box, screen, screenHandler, 8, 62, 79, 34, 0, 212, 34, 27, "슨", 280);
        // Green Balloon
        addBalloonButton(box, screen, screenHandler, 89, 62, 79, 34, 79, 212, 34, 35, "슦", 280);

        // Close button
        String backButton = "꣡";
        int closeV = 0;
        if (screen.getTitle().getString().contains(backButton)) {
            closeV = 28;
        }

        addSelectableElement(new UIButton(TEXTURE, 8, 9, 16, 14, 176, closeV, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(0);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(0).getStack()), (int) x, (int) y);
        }, false).setChildOf(box));
    }

    private void addBalloonButton(UIComponent box, Screen screen, ScreenHandler screenHandler, int x, int y, int width, int height, int u, int v, int hoveredVOffset, int slot, String disabledTitleChar, int vDisabled) {
        int finalV = v;
        int finalHoveredVOffset = hoveredVOffset;
        boolean disabled = disabledTitleChar != null && screen.getTitle().getString().contains(disabledTitleChar);
        if (disabled) {
            finalV = vDisabled;
            finalHoveredVOffset = 0;
        }

        UIComponent button = new UIButton(TEXTURE, x, y, width, height, u, finalV, finalHoveredVOffset, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(slot);
        }, (b, m, tx, ty) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), (int) tx, (int) ty);
        }, false).setChildOf(box);

        if (!disabled) {
            addSelectableElement(button);
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
