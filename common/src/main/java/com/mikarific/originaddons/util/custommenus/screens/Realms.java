package com.mikarific.originaddons.util.custommenus.screens;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIItem;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.custommenus.CustomMenus;
import com.mikarific.originaddons.util.custommenus.CustomScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Realms extends CustomScreen {
    private static UIComponent box;
    private static final Map<Integer, UIItem> items = new HashMap<>();
    private static final Map<Integer, UIButton> buttons = new HashMap<>();

    public static void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/realms.png");
        int TEXTURE_WIDTH = 260;
        int TEXTURE_HEIGHT = 138;
        box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 138) / 2, 176, 138, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Featured Realms
        if (screen.getTitle().getString().contains("섪")) {
            new UIButton(TEXTURE, 23, 15, 22, 28, 176, 44, 28, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(1);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(1).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        }
        //Teleport Home
        if (screen.getTitle().getString().contains("섫")) {
            new UIButton(TEXTURE, 58, 15, 60, 22, 176, 0, 22, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(4);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(4).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        }
        //Settings
        if (screen.getTitle().getString().contains("섬")) {
            new UIButton(TEXTURE, 131, 15, 22, 22, 236, 0, 22, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(7);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(7).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        }
        //Items
        for(int i = 19; i < 44; i++) {
            if ((i + 1) % 9 == 0) i += 2;
            int slot = i;
            int slotX = (8 + (slot * 18)) % 162;
            int slotY = (18 + (18 * (int) Math.floor(slot / 9.0)));
            items.put(slot, (UIItem) new UIItem(null, 0, 0, true, TEXTURE, slotX, slotY, 16, 16, slotX, slotY, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(slot);
            }, (b, m, x, y) -> {
                if (!screenHandler.getSlot(slot).getStack().getTranslationKey().equals("block.minecraft.air")) {
                    screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                }
            }, false).setChildOf(box));
        }
        //Page Buttons
        for (int i = 46; i < 53; i += 3) {
            int slot = i;
            buttons.put(slot, (UIButton) new UIButton(TEXTURE, 0, 0, 0, 0, 0, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(slot);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, CustomMenus.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), (int) x, (int) y);
            }, false).setChildOf(box));
        }
    }

    public static void draw(Screen screen) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        for (int i = 46; i < 53; i += 3) {
            if (screenHandler.getSlot(i).getStack().getNbt() != null) {
                int customModelData = Objects.requireNonNull(screenHandler.getSlot(i).getStack().getNbt()).getInt("CustomModelData");
                //Back Button
                if (customModelData == 8009) {
                    buttons.get(i).setX(box.getX() + 26);
                    buttons.get(i).setY(box.getY() + 111);
                    buttons.get(i).setWidth(16);
                    buttons.get(i).setHeight(10);
                    buttons.get(i).setU(198);
                    buttons.get(i).setV(44);
                    buttons.get(i).setHoveredVOffset(10);
                } else if (customModelData == 8010) {
                    buttons.get(i).setX(box.getX() + 26);
                    buttons.get(i).setY(box.getY() + 111);
                    buttons.get(i).setWidth(16);
                    buttons.get(i).setHeight(10);
                    buttons.get(i).setU(198);
                    buttons.get(i).setV(64);
                    buttons.get(i).setHoveredVOffset(0);
                }
                //Members Only
                if (customModelData == 8015) {
                    buttons.get(i).setX(box.getX() + 80);
                    buttons.get(i).setY(box.getY() + 108);
                    buttons.get(i).setWidth(15);
                    buttons.get(i).setHeight(16);
                    buttons.get(i).setU(245);
                    buttons.get(i).setV(44);
                    buttons.get(i).setHoveredVOffset(16);
                } else if (customModelData == 8016) {
                    buttons.get(i).setX(box.getX() + 80);
                    buttons.get(i).setY(box.getY() + 108);
                    buttons.get(i).setWidth(15);
                    buttons.get(i).setHeight(16);
                    buttons.get(i).setU(230);
                    buttons.get(i).setV(44);
                    buttons.get(i).setHoveredVOffset(16);
                }
                //Next Button
                if (customModelData == 8007) {
                    buttons.get(i).setX(box.getX() + 134);
                    buttons.get(i).setY(box.getY() + 111);
                    buttons.get(i).setWidth(16);
                    buttons.get(i).setHeight(10);
                    buttons.get(i).setU(214);
                    buttons.get(i).setV(44);
                    buttons.get(i).setHoveredVOffset(10);
                } else if (customModelData == 8008) {
                    buttons.get(i).setX(box.getX() + 134);
                    buttons.get(i).setY(box.getY() + 111);
                    buttons.get(i).setWidth(16);
                    buttons.get(i).setHeight(10);
                    buttons.get(i).setU(214);
                    buttons.get(i).setV(64);
                    buttons.get(i).setHoveredVOffset(0);
                }
            }
        }
        if (items.containsKey(19) && items.get(19).getStack() != screenHandler.slots.get(19).getStack()) {
            items.forEach((slotNumber, item) -> {
                Slot slot = screenHandler.slots.get(slotNumber);
                if (!slot.getStack().getTranslationKey().equals("block.minecraft.air")) {
                    item.setStack(slot.getStack());
                } else {
                    item.setStack(null);
                }
            });
        }
    }
}
