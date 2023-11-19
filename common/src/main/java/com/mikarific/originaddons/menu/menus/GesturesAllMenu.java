package com.mikarific.originaddons.menu.menus;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.*;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GesturesAllMenu extends CustomMenu {
    public static final String TITLE = "쉃";

    Identifier TEXTURE = new Identifier("originaddons", "textures/gui/custommenus/gestures_all.png");
    int TEXTURE_WIDTH = 248;
    int TEXTURE_HEIGHT = 124;

    private static ScreenHandler screenHandler;
    private static UIButton previousPage;
    private static UIButton nextPage;
    private static UIText pageNumbers;
    private static UIText slots;

    private static boolean firstPage;
    private static boolean lastPage;

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 124) / 2, 176, 124, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Slot Buttons
        for (int i = 9; i < 45; i++) {
            int slot = i;
            int slotX = (8 + (slot * 18)) % 162;
            int slotY = 9 + (18 * (int) Math.floor(slot / 9.0));
            addSelectableElement(new UIItem(null, 0, 0, true, TEXTURE, slotX, slotY, 16, 16, slotX, slotY, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                MenuUtils.pickupItemAtSlot(slot);
            }, (b, m, x, y) -> {
                if (!screenHandler.getSlot(slot).getStack().getItem().equals(Items.AIR)) {
                    MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), screen, m, (int) x, (int) y);
                }
            }, false).setChildOf(box), () -> !screenHandler.getSlot(slot).getStack().getItem().equals(Items.AIR));
        }

        //Previous Page
        previousPage = (UIButton) new UIButton(TEXTURE, 8, 101, 36, 14, 176, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(45).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);
        addSelectableElement(previousPage, () -> !firstPage);

        //Next Page
        nextPage = (UIButton) new UIButton(TEXTURE, 132, 101, 36, 14, 212, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(52);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(52).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);
        addSelectableElement(nextPage, () -> !lastPage);

        //Back
        if (screen.getTitle().getString().contains("쉄")) {
            addSelectableElement(new UIButton(TEXTURE, 8, 9, 16, 14, 192, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                MenuUtils.pickupItemAtSlot(0);
            }, (b, m, x, y) -> {
                MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(0).getStack()), screen, m, (int) x, (int) y);
            }, false).setChildOf(box));
        } else {
            addSelectableElement(new UIButton(TEXTURE, 8, 9, 16, 14, 176, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                MenuUtils.pickupItemAtSlot(0);
            }, (b, m, x, y) -> {
                MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(0).getStack()), screen, m, (int) x, (int) y);
            }, false).setChildOf(box));
        }
        //Page Numbers
        pageNumbers = (UIText) new UIText(Text.literal(""), 16777215, 80, 104).setChildOf(box);
        //Slots
        slots = (UIText) new UIText(Text.literal(""), 16777215, 8, -3).setChildOf(box);
    }

    @Override
    public void update(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        if (screen.getTitle().getString().contains("쉅")) {
            previousPage.setAction(() -> {});
            previousPage.setU(176);
            previousPage.setV(56);
            previousPage.setHoveredVOffset(0);
            firstPage = true;
        } else {
            previousPage.setAction(() -> {
                MenuUtils.pickupItemAtSlot(45);
            });
            previousPage.setU(176);
            previousPage.setV(28);
            previousPage.setHoveredVOffset(14);
            firstPage = false;
        }

        if (screen.getTitle().getString().contains("쉆")) {
            nextPage.setAction(() -> {});
            nextPage.setU(212);
            nextPage.setV(56);
            nextPage.setHoveredVOffset(0);
            lastPage = true;
        } else {
            nextPage.setAction(() -> {
                MenuUtils.pickupItemAtSlot(52);
            });
            nextPage.setU(212);
            nextPage.setV(28);
            nextPage.setHoveredVOffset(14);
            lastPage = false;
        }

        pageNumbers.setText(Text.literal(screen.getTitle().getSiblings().get(1).getSiblings().get(0).getString()).setStyle(Style.EMPTY));
        slots.setText(screen.getTitle().getSiblings().get(0).getSiblings().get(1));
    }

    @Override
    protected void draw(Screen screen) {}

    @Override
    public void close(Screen screen) {
        previousPage = null;
        nextPage = null;
        pageNumbers = null;
        slots = null;
        screenHandler = null;
        firstPage = false;
        lastPage = false;
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
