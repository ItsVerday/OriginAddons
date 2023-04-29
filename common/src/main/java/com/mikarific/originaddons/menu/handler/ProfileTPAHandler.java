package com.mikarific.originaddons.menu.handler;

import com.mikarific.originaddons.ui.TextureWidget;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileTPAHandler {
    public static final String TITLE = "쇉";
    public static final String TITLE_OTHER = "쉋";
    public static final Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/profile_tpa.png");
    public static final int TEXTURE_WIDTH = 49;
    public static final int TEXTURE_HEIGHT = 41;

    public static List<Drawable> handleScreen(Screen screen) {
        String title = screen.getTitle().getString();
        if (!title.contains(TITLE) && !title.contains(TITLE_OTHER)) return new ArrayList<>();
        List<Drawable> drawables = new ArrayList<>();

        int x = (screen.width - 176) / 2;
        int y = (screen.height - 178) / 2;
        if (title.contains(TITLE_OTHER)) {
            y -= 18;
        }

        drawables.add(new TextureWidget(x, y - 10, 49, 13, 0, 0, TEXTURE, TEXTURE_WIDTH, TEXTURE_HEIGHT));
        drawables.add(new TexturedButtonWidget(x + 7, y - 5, 16, 14, 0, 13, 14, TEXTURE, TEXTURE_WIDTH, TEXTURE_HEIGHT, button -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            String username = getUsername();
            if (username != null) MenuUtils.sendCommand(player, "/tpa " + username);
        }));
        drawables.add(new TexturedButtonWidget(x + 25, y - 5, 16, 14, 16, 13, 14, TEXTURE, TEXTURE_WIDTH, TEXTURE_HEIGHT, button -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            String username = getUsername();
            if (username != null) MenuUtils.sendCommand(player, "/tpahere " + username);
        }));

        return drawables;
    }

    private static String getUsername() {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;
        Slot slot = screenHandler.slots.get(21);
        if (slot.getStack().getNbt() != null) return Objects.requireNonNull(slot.getStack().getNbt()).getCompound("SkullOwner").getString("Name");
        return null;
    }
}