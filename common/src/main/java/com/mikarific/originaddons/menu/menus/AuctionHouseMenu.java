package com.mikarific.originaddons.menu.menus;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.*;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;

public class AuctionHouseMenu extends CustomMenu {
    public static final String ALL_AUCTIONS_TITLE = "숂";
    public static final String SELF_AUCTIONS_TITLE = "숃";
    public static final String AUCTION_DETAILS_TITLE = "숅";
    public static final String AUCTION_SEARCH_TITLE = "숚";

    public static final String BUTTON_BACK = "숉";
    public static final String BUTTON_REFRESH = "숈";
    public static final String BUTTON_CONFIRM = "수";
    public static final String BUTTON_CONFIRM_OFF = "숙";
    public static final String BUTTON_FIRST_PAGE = "숆";
    public static final String BUTTON_LAST_PAGE = "숇";

    public static final String GROUP_BLOCKS = "숊";
    public static final String GROUP_ENCHANTING = "숋";
    public static final String GROUP_FARMING = "숌";
    public static final String GROUP_FURNITURE = "숍";
    public static final String GROUP_GEAR = "숎";
    public static final String GROUP_MINERALS = "숏";
    public static final String GROUP_OTHER = "숐";
    public static final String GROUP_SEASONAL = "숑";
    public static final String GROUP_TOKENS = "숒";
    public static final String GROUP_CURRENT = "숛";
    public static final String GROUP_SOLD = "순";

    public static final String SORT_ENDING = "숓";
    public static final String SORT_HIGHEST = "숔";
    public static final String SORT_ITEM = "숕";
    public static final String SORT_LOWEST = "숖";
    public static final String SORT_RECENT = "숗";

    private static Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/auction_house.png");
    private static int TEXTURE_WIDTH = 352;
    private static int TEXTURE_HEIGHT = 318;

    private static Map<Integer, UIItem> items = new HashMap<>();

    private static ScreenHandler screenHandler;
    private static String menuType = "";
    private static UITexture box;
    private static UIButton backButton;
    private static UIButton refreshButton;
    private static boolean refreshEnabled = false;
    private static UIButton buyButton;
    private static boolean buyEnabled = false;
    private static UIButton previousPageButton;
    private static boolean previousPageEnabled = false;
    private static UIButton nextPageButton;
    private static boolean nextPageEnabled = false;
    private static UIButton categoryButton;
    private static UIButton sortButton;
    private static UIText pageText;

    private void setMenuType(String title) {
        String oldMenuType = menuType;
        if (title.contains(ALL_AUCTIONS_TITLE)) {
            menuType = "all";
        } else if (title.contains(SELF_AUCTIONS_TITLE)) {
            menuType = "self";
        } else if (title.contains(AUCTION_DETAILS_TITLE)) {
            menuType = "details";
        } else if (title.contains(AUCTION_SEARCH_TITLE)) {
            menuType = "search";
        }

        if (!menuType.equals(oldMenuType)) {
            for (UIItem item: items.values()) {
                item.setStack(null);
            }
        }
    }

    @Override
    protected void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        box = (UITexture) new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 124) / 2, 176, 124, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
        pageText = (UIText) new UIText(Text.literal(""), 16777215, 8, -3).setChildOf(box);

        backButton = (UIButton) new UIButton(TEXTURE, 8, 9, 16, 14, 0, 248, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(0);
        }, (b, m, x, y) -> {
            ItemStack stack = screenHandler.getSlot(0).getStack();
            if (!stack.getItem().equals(Items.AIR)) {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(stack), (int)x, (int)y);
            }
        }, false).setChildOf(box);
        addSelectableElement(backButton);

        refreshButton = (UIButton) new UIButton(TEXTURE, 26, 9, 16, 14, 48, 276, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(1);
        }, (b, m, x, y) -> {
            ItemStack stack = screenHandler.getSlot(1).getStack();
            if (!stack.getItem().equals(Items.AIR)) {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(stack), (int)x, (int)y);
            }
        }, false).setChildOf(box);
        addSelectableElement(refreshButton, () -> refreshEnabled);

        buyButton = (UIButton) new UIButton(TEXTURE, 134, 9, 34, 14, 170, 248, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(8);
        }, (b, m, x, y) -> {
            ItemStack stack = screenHandler.getSlot(8).getStack();
            if (!stack.getItem().equals(Items.AIR)) {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(stack), (int)x, (int)y);
            }
        }, false).setChildOf(box);
        addSelectableElement(buyButton, () -> buyEnabled);

        previousPageButton = (UIButton) new UIButton(TEXTURE, 8, 101, 36, 14, 64, 248, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(45);
        }, (b, m, x, y) -> {
            ItemStack stack = screenHandler.getSlot(45).getStack();
            if (!stack.getItem().equals(Items.AIR)) {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(stack), (int)x, (int)y);
            }
        }, false).setChildOf(box);
        addSelectableElement(previousPageButton, () -> previousPageEnabled);

        nextPageButton = (UIButton) new UIButton(TEXTURE, 96, 101, 36, 14, 100, 248, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(50);
        }, (b, m, x, y) -> {
            ItemStack stack = screenHandler.getSlot(50).getStack();
            if (!stack.getItem().equals(Items.AIR)) {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(stack), (int)x, (int)y);
            }
        }, false).setChildOf(box);
        addSelectableElement(nextPageButton, () -> nextPageEnabled);

        categoryButton = (UIButton) new UIButton(TEXTURE, 134, 101, 16, 14, 176, 290, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(52);
        }, (b, m, x, y) -> {
            ItemStack stack = screenHandler.getSlot(52).getStack();
            if (!stack.getItem().equals(Items.AIR)) {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(stack), (int)x, (int)y);
            }
        }, false).setChildOf(box);
        addSelectableElement(categoryButton);

        sortButton = (UIButton) new UIButton(TEXTURE, 152, 101, 16, 14, 284, 248, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(53);
        }, (b, m, x, y) -> {
            ItemStack stack = screenHandler.getSlot(53).getStack();
            if (!stack.getItem().equals(Items.AIR)) {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(stack), (int)x, (int)y);
            }
        }, false).setChildOf(box);
        addSelectableElement(sortButton);

        addUIItem(4, 0, TEXTURE, screen, box);
        for (int y = 1; y < 5; y++) {
            for (int x = 0; x < 9; x++) {
                addUIItem(x, y, TEXTURE, screen, box);
            }
        }
    }

    private static UIItem addUIItem(int x, int y, Identifier texture, Screen screen, UIComponent box) {
        int slot = x + y * 9;
        int slotX = 8 + (x * 18);
        int slotY = 9 + (y * 18);

        ItemStack previousItem = null;
        if (items.containsKey(slot)) {
            previousItem = items.get(slot).getStack();
        }

        UIItem item = (UIItem) new UIItem(previousItem, 0, 0, true, texture, slotX, slotY, 16, 16, 320, 288, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(slot);
        }, (b, m, x_, y_) -> {
            ItemStack stack = screenHandler.getSlot(slot).getStack();
            if (!stack.getItem().equals(Items.AIR)) {
                screen.renderTooltip(m, MenuUtils.getDisplayTooltip(stack), (int) x_, (int) y_);
            }
        }, false).setChildOf(box);

        items.put(slot, item);
        return item;
    }

    @Override
    public void update(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        String title = screen.getTitle().toString();
        setMenuType(title);

        // This is a really bad way of removing the GUI background
        String stepGUI = "\uF82C\uF82A\uF829\uF821";
        String json = Text.Serializer.toJson(screen.getTitle());
        json = json.replaceAll(ALL_AUCTIONS_TITLE, stepGUI);
        json = json.replaceAll(SELF_AUCTIONS_TITLE, stepGUI);
        json = json.replaceAll(AUCTION_DETAILS_TITLE, stepGUI);
        json = json.replaceAll(AUCTION_SEARCH_TITLE, stepGUI);
        pageText.setText(Text.Serializer.fromJson(json));

        if (menuType.equals("all")) {
            box.setU(0);
            box.setV(0);
        } else if (menuType.equals("self")) {
            box.setU(176);
            box.setV(124);
        } else if (menuType.equals("details")) {
            box.setU(0);
            box.setV(124);
        } else if (menuType.equals("search")) {
            box.setU(176);
            box.setV(0);
        }

        if (title.contains(BUTTON_BACK)) {
            backButton.setU(32);
            backButton.setV(248);
        } else if (menuType.equals("all")) {
            backButton.setU(0);
            backButton.setV(248);
        } else {
            backButton.setU(16);
            backButton.setV(248);
        }

        if (title.contains(BUTTON_REFRESH)) {
            refreshButton.setU(48);
            refreshButton.setV(248);
            refreshButton.setHoveredVOffset(14);
            refreshEnabled = true;
        } else {
            refreshButton.setU(48);
            refreshButton.setV(276);
            refreshButton.setHoveredVOffset(0);
            refreshEnabled = false;
        }

        if (title.contains(BUTTON_CONFIRM)) {
            buyButton.setU(136);
            buyButton.setV(248);
            buyButton.setHoveredVOffset(14);
            buyButton.setVisible(true);
            buyEnabled = true;
        } else if (title.contains(BUTTON_CONFIRM_OFF)) {
            buyButton.setU(136);
            buyButton.setV(276);
            buyButton.setHoveredVOffset(0);
            buyButton.setVisible(true);
            buyEnabled = true;
        } else if (menuType.equals("all")) {
            buyButton.setU(170);
            buyButton.setV(248);
            buyButton.setHoveredVOffset(14);
            buyButton.setVisible(true);
            buyEnabled = true;
        } else {
            buyButton.setVisible(false);
            buyEnabled = false;
        }

        if (title.contains(BUTTON_FIRST_PAGE)) {
            previousPageButton.setU(64);
            previousPageButton.setV(276);
            previousPageButton.setHoveredVOffset(0);
            previousPageEnabled = false;
        } else {
            previousPageButton.setU(64);
            previousPageButton.setV(248);
            previousPageButton.setHoveredVOffset(14);
            previousPageEnabled = true;
        }

        if (title.contains(BUTTON_LAST_PAGE)) {
            nextPageButton.setU(100);
            nextPageButton.setV(276);
            nextPageButton.setHoveredVOffset(0);
            nextPageEnabled = false;
        } else {
            nextPageButton.setU(100);
            nextPageButton.setV(248);
            nextPageButton.setHoveredVOffset(14);
            nextPageEnabled = true;
        }

        if (title.contains(GROUP_BLOCKS)) {
            categoryButton.setU(16);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_ENCHANTING)) {
            categoryButton.setU(48);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_FARMING)) {
            categoryButton.setU(64);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_FURNITURE)) {
            categoryButton.setU(80);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_GEAR)) {
            categoryButton.setU(96);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_MINERALS)) {
            categoryButton.setU(112);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_OTHER)) {
            categoryButton.setU(128);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_SEASONAL)) {
            categoryButton.setU(144);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_TOKENS)) {
            categoryButton.setU(0);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_CURRENT)) {
            categoryButton.setU(32);
            categoryButton.setV(290);
        } else if (title.contains(GROUP_SOLD)) {
            categoryButton.setU(160);
            categoryButton.setV(290);
        } else {
            categoryButton.setU(176);
            categoryButton.setV(290);
        }

        if (title.contains(SORT_ENDING)) {
            sortButton.setU(204);
            sortButton.setV(248);
        } else if (title.contains(SORT_HIGHEST)) {
            sortButton.setU(220);
            sortButton.setV(248);
        } else if (title.contains(SORT_ITEM)) {
            sortButton.setU(236);
            sortButton.setV(248);
        } else if (title.contains(SORT_LOWEST)) {
            sortButton.setU(252);
            sortButton.setV(248);
        } else if (title.contains(SORT_RECENT)) {
            sortButton.setU(268);
            sortButton.setV(248);
        } else {
            sortButton.setU(284);
            sortButton.setV(248);
        }
    }

    @Override
    protected void draw(Screen screen) {
        items.forEach((slotNumber, item) -> {
            Slot slot = screenHandler.slots.get(slotNumber);
            if (!slot.getStack().getItem().equals(Items.AIR)) {
                item.setStack(slot.getStack());
            } else {
                item.setStack(null);
            }

            item.setVisible(false);
        });

        if (menuType.equals("all")) {
            for (int y = 1; y < 5; y++) {
                for (int x = 0; x < 9; x++) {
                    items.get(x + y * 9).setVisible(true);
                }
            }
        } else if (menuType.equals("self")) {
            items.get(4).setVisible(true);
            for (int y = 2; y < 5; y++) {
                for (int x = 0; x < 9; x++) {
                    items.get(x + y * 9).setVisible(true);
                }
            }
        } else if (menuType.equals("details")) {
            items.get(4).setVisible(true);
            for (int y = 2; y < 5; y++) {
                for (int x = 0; x < 9; x++) {
                    items.get(x + y * 9).setVisible(true);
                }
            }
        } else if (menuType.equals("search")) {
            for (int y = 2; y < 5; y++) {
                for (int x = 0; x < 9; x++) {
                    items.get(x + y * 9).setVisible(true);
                }
            }
        }
    }

    @Override
    public void close(Screen screen) {
        items.clear();
        screenHandler = null;
        menuType = "";
        box = null;
        backButton = null;
        refreshButton = null;
        refreshEnabled = false;
        buyButton = null;
        buyEnabled = false;
        previousPageButton = null;
        previousPageEnabled = false;
        nextPageButton = null;
        nextPageEnabled = false;
        categoryButton = null;
        sortButton = null;
        pageText = null;
    }

    @Override
    public boolean isEnabled() {
        return OriginAddons.getConfig().customAuctionsMenu;
    }

    @Override
    protected boolean matches(Screen screen) {
        String str = screen.getTitle().getString();
        return str.contains(ALL_AUCTIONS_TITLE) || str.contains(SELF_AUCTIONS_TITLE) || str.contains(AUCTION_DETAILS_TITLE) || str.contains(AUCTION_SEARCH_TITLE);
    }

    @Override
    public String getTitle() {
        return null;
    }
}