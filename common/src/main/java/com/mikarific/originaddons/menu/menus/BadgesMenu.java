package com.mikarific.originaddons.menu.menus;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIItem;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

public class BadgesMenu extends CustomMenu {
    public static final String TITLE = "솷";

    private static UIItem face;

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "textures/gui/custommenus/badges.png");
        int TEXTURE_WIDTH = 260;
        int TEXTURE_HEIGHT = 99;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 196) / 2, (screen.height - 99) / 2, 196, 99, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Face
        face = (UIItem) new UIItem(null, 6, 6, false, TEXTURE, 84, 25, 28, 28, 196, 0, 28, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(4).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        addSelectableElement(face);
        //Farming
        addSelectableElement(new UIButton(TEXTURE, 36, 67, 16, 16, 196, 56, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(19).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Combat
        addSelectableElement(new UIButton(TEXTURE, 72, 67, 16, 16, 212, 56, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(21).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Exploration
        addSelectableElement(new UIButton(TEXTURE, 108, 67, 16, 16, 228, 56, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(23).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Magic
        addSelectableElement(new UIButton(TEXTURE, 144, 67, 16, 16, 244, 56, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(25).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
    }

    @Override
    protected void draw(Screen screen) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        if (face.getStack() == null) {
            Slot slot = screenHandler.slots.get(4);
            if (!slot.getStack().getTranslationKey().equals("block.minecraft.air")) face.setStack(slot.getStack());
        }
    }

    @Override
    public void close(Screen screen) {
        face = null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
