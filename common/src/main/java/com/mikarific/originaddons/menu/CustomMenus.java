package com.mikarific.originaddons.menu;

import com.mikarific.originaddons.menu.menus.*;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class CustomMenus {
    private static List<CustomMenu> menus = new ArrayList<>();
    private static CustomMenu currentMenu = null;

    public static void init() {
        menus.add(new NavigatorMenu());
        menus.add(new NavigatorBalloonsMenu());
        menus.add(new PaintingMenu());
        menus.add(new ProfileMenu());
        menus.add(new ProfileStaffMenu());
    }

    public static CustomMenu getMenuForScreen(Screen screen) {
        for (CustomMenu menu: menus) {
            if (menu.matchScreen(screen)) return menu;
        }

        return null;
    }

    public static CustomMenu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(CustomMenu currentMenu) {
        CustomMenus.currentMenu = currentMenu;
    }

    public static boolean inventoryEnabled() {
        if (getCurrentMenu() == null) return true;
        return getCurrentMenu().inventoryEnabled();
    }
}