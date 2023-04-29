package com.mikarific.originaddons.menu;

import com.mikarific.originaddons.menu.handler.ProfileTPAHandler;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class ScreenHandler {
    public static List<Drawable> handleScreen(Screen screen) {
        List<Drawable> drawables = new ArrayList<>();
        drawables.addAll(ProfileTPAHandler.handleScreen(screen));

        return drawables;
    }
}