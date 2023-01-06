package com.mikarific.originaddons.menu;

import com.mikarific.originaddons.menu.menus.ProfileMenu;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class CustomMenus {
    private static List<CustomMenu> menus = new ArrayList<>();

    public static void init() {
        menus.add(new ProfileMenu());
    }

    public static CustomMenu getMenuForScreen(Screen screen) {
        for (CustomMenu menu: menus) {
            if (menu.matchScreen(screen)) return menu;
        }

        return null;
    }
}