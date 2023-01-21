package com.mikarific.originaddons.menu.menus;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.*;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileStaffMenu extends CustomMenu {
    public static final String TITLE = "쉋";

    private static UIItem face;
    private static final Map<Integer, UIItem> items = new HashMap<>();
    private static UIButton onlineIndicator;
    private static String username = "";
    private static UIText actionText1;
    private static UIText actionText2;
    private static UIText actionText3;

    @Override
    public void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ScreenHandler screenHandler = player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/profile_staff.png");
        int TEXTURE_WIDTH = 332;
        int TEXTURE_HEIGHT = 142;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 123) / 2, 176, 123, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);

        //Face
        face = (UIItem) new UIItem(null, 49, 30, false, TEXTURE, 13, 28, 42, 42, 176, 0, 42, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(10).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);

        //Items
        for (int i = 7; i < 36; i++) {
            if (i % 9 == 0) i += 7;
            int slot = i;
            int slotX = (8 + (slot * 18)) % 162;
            int slotY = 4 + (18 * ((int) Math.floor(slot / 9.0) + 1));
            items.put(slot, (UIItem) new UIItem(null, 0, 0, true, TEXTURE, slotX, slotY, 16, 16, 273, 84, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                if (!screenHandler.getSlot(slot).getStack().getTranslationKey().equals("block.minecraft.air")) {
                    screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                }
            }, false).setChildOf(box));
        }

        //Rank Tag
        int tag = 0;
        if (screen.getTitle().getString().contains("숨")) tag = 7; //VIP
        if (screen.getTitle().getString().contains("숩")) tag = 14; //PRO
        if (screen.getTitle().getString().contains("숪")) tag = 21; //MVP
        if (screen.getTitle().getString().contains("숫")) tag = 28; //ELITE
        if (screen.getTitle().getString().contains("숬")) tag = 35; //CREATOR
        if (screen.getTitle().getString().contains("숭")) tag = 42; //MOD
        if (screen.getTitle().getString().contains("숮")) tag = 49; //TEAM
        if (screen.getTitle().getString().contains("숯")) tag = 56; //ADMIN
        new UITexture(TEXTURE, 60, 31, 46, 7, 284, tag, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(box);
        //Alpha & Beta Tag
        if (screen.getTitle().getString().contains("숝")) new UITexture(TEXTURE, 60, 41, 34, 7, 284, 63, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(box); //Alpha
        if (screen.getTitle().getString().contains("숞")) new UITexture(TEXTURE, 60, 41, 34, 7, 284, 70, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(box); //Beta

        //Player Level
        new UIButton(TEXTURE, 65, 60, 13, 13, 176, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(21).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Rubies
        new UIButton(TEXTURE, 79, 60, 16, 13, 189, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(22).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Discord
        new UIButton(TEXTURE, 96, 60, 16, 13, 205, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(23).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Playtime
        new UIButton(TEXTURE, 113, 60, 13, 13, 221, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(24).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);

        //Online Indicator
        onlineIndicator = (UIButton) new UIButton(TEXTURE, 116, 22, 13, 13, 234, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(6).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);

        //Visit Realm
        addSelectableElement(new UIButton(TEXTURE, 7, 79, 34, 14, 218, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(27);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(27).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Friend
        int friendU = 252;
        int friendV = 0;
        if (screen.getTitle().getString().contains("쉤")) friendU = 268; //Friend Remove
        if (screen.getTitle().getString().contains("쉣")) {
            friendU = 218;
            friendV = 28;
        }
        addSelectableElement(new UIButton(TEXTURE, 43, 79, 16, 14, friendU, friendV, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(29);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(29).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));
        //Duel
        new UIButton(TEXTURE, 61, 79, 16, 14, 234, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(30);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(30).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Trade
        new UIButton(TEXTURE, 79, 79, 16, 14, 250, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(31);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(31).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Vault
        new UIButton(TEXTURE, 97, 79, 16, 14, 266, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(32);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(32).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        //Auctions
        addSelectableElement(new UIButton(TEXTURE, 115, 79, 16, 14, 218, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(33);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(33).getStack()), (int)x, (int)y);
        }, false).setChildOf(box));

        //Messaging
        new UIButton(TEXTURE, 7, 5, 16, 14, 234, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            screen.renderTooltip(m, Text.literal((Text.translatable("originaddons.menus.profile.messaging").getString().replaceAll("%username%", username))).setStyle(Style.EMPTY.withColor(TextColor.parse("gray"))), (int)x, (int)y);
        }, false).setChildOf(box);
        //TPA
        addSelectableElement(new UIButton(TEXTURE, 25, 5, 16, 14, 250, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.sendMessage(player, "/tpa " + username);
            player.closeHandledScreen();
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, Text.literal((Text.translatable("originaddons.menus.profile.tpa").getString().replaceAll("%username%", username))).setStyle(Style.EMPTY.withColor(TextColor.parse("gray"))), (int)x, (int)y);
        }, true).setChildOf(box));
        //TPAHere
        addSelectableElement(new UIButton(TEXTURE, 43, 5, 16, 14, 266, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.sendMessage(player, "/tpahere " + username);
            player.closeHandledScreen();
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, Text.literal((Text.translatable("originaddons.menus.profile.tpahere").getString().replaceAll("%username%", username))).setStyle(Style.EMPTY.withColor(TextColor.parse("gray"))), (int)x, (int)y);
        }, true).setChildOf(box));

        // Action 1
        UIButton actionButton1 = (UIButton) new UIButton(TEXTURE, 7, 96, 52, 16, 176, 110, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(36);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(36).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        actionText1 = new UIText(Text.literal(""), 16777215, 10, 4);
        actionText1.setChildOf(actionButton1);

        // Action 2
        UIButton actionButton2 = (UIButton) new UIButton(TEXTURE, 61, 96, 52, 16, 280, 110, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(39);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(39).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        actionText2 = new UIText(Text.literal(""), 16777215, 6, 4);
        actionText2.setChildOf(actionButton2);

        //Invsee
        UIButton actionButton3 = (UIButton) new UIButton(TEXTURE, 115, 96, 52, 16, 228, 110, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(42);
        }, (b, m, x, y) -> {
            screen.renderTooltip(m, MenuUtils.getDisplayTooltip(screenHandler.getSlot(42).getStack()), (int)x, (int)y);
        }, false).setChildOf(box);
        actionText3 = new UIText(Text.literal(""), 16777215, 5, 4);
        actionText3.setChildOf(actionButton3);
    }

    @Override
    public void draw(Screen screen) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        Style style = Style.EMPTY.withColor(TextColor.parse("white"));
        String text1 = screenHandler.getSlot(36).getStack().getName().getString();
        actionText1.setText(Text.literal(text1.substring(0, Math.min(text1.length(), 6))).setStyle(style));
        String text2 = screenHandler.getSlot(39).getStack().getName().getString();
        actionText2.setText(Text.literal(text2.substring(0, Math.min(text2.length(), 8))).setStyle(style));
        String text3 = screenHandler.getSlot(42).getStack().getName().getString();
        actionText3.setText(Text.literal(text3.substring(0, Math.min(text3.length(), 8))).setStyle(style));

        if (username.equals("")) {
            Slot slot = screenHandler.slots.get(21);
            if (slot.getStack().getNbt() != null) username = Objects.requireNonNull(slot.getStack().getNbt()).getCompound("SkullOwner").getString("Name");
        }

        if (face.getStack() == null) {
            Slot slot = screenHandler.slots.get(21);
            if (!slot.getStack().getTranslationKey().equals("block.minecraft.air")) face.setStack(slot.getStack());
        }
        if (items.containsKey(7) && items.get(7).getStack() == null) {
            items.forEach((slotNumber, item) -> {
                Slot slot = screenHandler.slots.get(slotNumber);
                if (!slot.getStack().getTranslationKey().equals("block.minecraft.air")) item.setStack(slot.getStack());
            });
        }
        if (screenHandler.slots.get(6).getStack().getNbt() != null) {
            int status = Objects.requireNonNull(screenHandler.slots.get(6).getStack().getNbt()).getInt("CustomModelData");
            if (status == 8042) { //Online
                onlineIndicator.setU(260).setV(84);
            }
            if (status == 8044) { //AFK
                onlineIndicator.setU(247).setV(84);
            }
            if (status == 8043) { //Offline
                onlineIndicator.setU(234).setV(84);
            }
        }
    }

    @Override
    public void close(Screen screen) {
        username = "";
        items.clear();
        face = null;
        onlineIndicator = null;
    }

    @Override
    public boolean isEnabled() {
        return OriginAddons.getConfig().customProfileMenu;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}