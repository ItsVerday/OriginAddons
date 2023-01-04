package com.mikarific.originaddons.util.custommenus;

import com.mikarific.originaddons.ui.Window;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

public class CustomScreen {
    public static boolean inventoryEnabled() {
        return false;
    }
    public static List<Integer> getAllowedSlots() {
        return new ArrayList<>();
    }

    public static void init(Screen screen, Window window) {}

    public static void draw(Screen screen) {}

    public static void mouseClicked(CallbackInfoReturnable<Boolean> cir) {}
}
