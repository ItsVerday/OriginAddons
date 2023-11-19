package com.mikarific.originaddons.menu.menus;

import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIText;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class GesturesMenu extends CustomMenu {
    public static final String TITLE = "쉂";

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "textures/gui/custommenus/gestures_favorites.png");
        int TEXTURE_WIDTH = 384;
        int TEXTURE_HEIGHT = 136;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 106) / 2, 176, 106, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Slot 1
        addSelectableElement(new UIButton(TEXTURE, 8, 27, 52, 34, 176, 0, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(9);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(9).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Slot 2
        addSelectableElement(new UIButton(TEXTURE, 62, 27, 52, 34, 228, 0, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(12);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(12).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Slot 3
        addSelectableElement(new UIButton(TEXTURE, 116, 27, 52, 34, 280, 0, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(15);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(15).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Slot 4
        addSelectableElement(new UIButton(TEXTURE, 8, 63, 52, 34, 176, 68, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(27);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(27).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Slot 5
        addSelectableElement(new UIButton(TEXTURE, 62, 63, 52, 34, 228, 68, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(30);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(30).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Slot 6
        addSelectableElement(new UIButton(TEXTURE, 116, 63, 52, 34, 280, 68, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(33);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(33).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));

        //Back
        new UIButton(TEXTURE, 8, 9, 16, 14, 332, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(0);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(0).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box);
        //View All
        addSelectableElement(new UIButton(TEXTURE, 116, 9, 52, 14, 332, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(6);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(6).getStack()), screen, m, (int)x, (int)y);
        }, false).setChildOf(box));
        //Gestures
        List<Text> gestures = screen.getTitle().getSiblings().get(1).getSiblings().get(0).getSiblings().stream().filter(sibling -> sibling.getStyle().getFont().getPath().contains("gesture")).toList();
        for (int i = 0; i < gestures.size(); i++) {
            if (i == 0) new UIText(gestures.get(i), 16777215, 18, -3).setChildOf(box);
            if (i == 1) new UIText(gestures.get(i), 16777215, 73, -3).setChildOf(box);
            if (i == 2) new UIText(gestures.get(i), 16777215, 128, -3).setChildOf(box);
            if (i == 3) new UIText(gestures.get(i), 16777215, 21, -3).setChildOf(box);
            if (i == 4) new UIText(gestures.get(i), 16777215, 76, -3).setChildOf(box);
            if (i == 5) new UIText(gestures.get(i), 16777215, 131, -3).setChildOf(box);
        }
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