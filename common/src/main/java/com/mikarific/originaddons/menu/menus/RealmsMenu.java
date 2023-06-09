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
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RealmsMenu extends CustomMenu {
    public static final String TITLE = "섩";

    private static UIComponent box;
    private static final Map<Integer, UIItem> items = new HashMap<>();
    private static final Map<Integer, UIButton> buttons = new HashMap<>();

    private static boolean firstPage = false;
    private static boolean lastPage = false;

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "textures/gui/custommenus/realms.png");
        int TEXTURE_WIDTH = 260;
        int TEXTURE_HEIGHT = 138;
        box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 138) / 2, 176, 138, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        //Featured Realms
        if (screen.getTitle().getString().contains("섪")) {
            new UIButton(TEXTURE, 23, 15, 22, 28, 176, 44, 28, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                MenuUtils.pickupItemAtSlot(1);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(1).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        }
        //Teleport Home
        if (screen.getTitle().getString().contains("섫")) {
            addSelectableElement(new UIButton(TEXTURE, 58, 15, 60, 22, 176, 0, 22, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                MenuUtils.pickupItemAtSlot(4);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(4).getStack()), (int)x, (int)y);
            }, false).setChildOf(box));
        }
        //Settings
        if (screen.getTitle().getString().contains("섬")) {
            addSelectableElement(new UIButton(TEXTURE, 131, 15, 22, 22, 236, 0, 22, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                MenuUtils.pickupItemAtSlot(7);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(7).getStack()), (int)x, (int)y);
            }, false).setChildOf(box));
        }
        //Items
        for(int i = 19; i < 44; i++) {
            if ((i + 1) % 9 == 0) i += 2;
            int slot = i;
            int slotX = (8 + (slot * 18)) % 162;
            int slotY = (18 + (18 * (int) Math.floor(slot / 9.0)));
            UIItem item = (UIItem) new UIItem(null, 0, 0, true, TEXTURE, slotX, slotY, 16, 16, slotX, slotY, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                MenuUtils.pickupItemAtSlot(slot);
            }, (b, m, x, y) -> {
                if (!screenHandler.getSlot(slot).getStack().getTranslationKey().equals("block.minecraft.air")) {
                    screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                }
            }, false).setChildOf(box);
            items.put(slot, item);
            addSelectableElement(item, () ->  !screenHandler.getSlot(slot).getStack().getItem().equals(Items.AIR));
        }
        //Page Buttons
        for (int i = 46; i < 53; i += 3) {
            int slot = i;
            UIButton button = (UIButton) new UIButton(TEXTURE, 0, 0, 0, 0, 0, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                MenuUtils.pickupItemAtSlot(slot);
            }, (b, m, x, y) -> {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), (int) x, (int) y);
            }, false).setChildOf(box);
            buttons.put(slot, button);
            addSelectableElement(button, i == 46 ? () -> !firstPage : i == 52 ? () -> !lastPage : () -> true);
        }
    }

    @Override
    protected void draw(Screen screen) {
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
                    firstPage = false;
                } else if (customModelData == 8010) {
                    buttons.get(i).setX(box.getX() + 26);
                    buttons.get(i).setY(box.getY() + 111);
                    buttons.get(i).setWidth(16);
                    buttons.get(i).setHeight(10);
                    buttons.get(i).setU(198);
                    buttons.get(i).setV(64);
                    buttons.get(i).setHoveredVOffset(0);
                    firstPage = true;
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
                    lastPage = false;
                } else if (customModelData == 8008) {
                    buttons.get(i).setX(box.getX() + 134);
                    buttons.get(i).setY(box.getY() + 111);
                    buttons.get(i).setWidth(16);
                    buttons.get(i).setHeight(10);
                    buttons.get(i).setU(214);
                    buttons.get(i).setV(64);
                    buttons.get(i).setHoveredVOffset(0);
                    lastPage = true;
                }
            }
        }
        if (items.containsKey(19) && items.get(19).getStack() != screenHandler.slots.get(19).getStack()) {
            items.forEach((slotNumber, item) -> {
                Slot slot = screenHandler.slots.get(slotNumber);
                if (!slot.getStack().getItem().equals(Items.AIR)) {
                    item.setStack(slot.getStack());
                } else {
                    item.setStack(null);
                }
            });
        }
    }

    @Override
    public void close(Screen screen) {
        box = null;
        items.clear();
        buttons.clear();
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
