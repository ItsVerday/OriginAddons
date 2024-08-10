package com.mikarific.originaddons.menu.menus;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIItem;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.ItemStackUtils;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProfileMenu extends CustomMenu {
    public static final String TITLE = "쇉";

    private static UIItem face;
    private static final Map<Integer, UIItem> items = new HashMap<>();
    private static UIButton onlineIndicator;
    private static String username = "";

    @Override
    public void init(Screen screen, Window window) {
        assert MinecraftClient.getInstance().player != null;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ScreenHandler screenHandler = player.currentScreenHandler;

        Identifier TEXTURE = new Identifier("originaddons", "textures/gui/custommenus/profile.png");
        int TEXTURE_WIDTH = 330;
        int TEXTURE_HEIGHT = 110;
        UIComponent box = new UITexture(TEXTURE, (screen.width - 176) / 2, (screen.height - 104) / 2, 176, 104, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);

        //Face
        face = (UIItem) new UIItem(null, 49, 30, false, TEXTURE, 13, 28, 42, 42, 176, 0, 42, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(10).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);

        //Items
        for (int i = 7; i < 36; i++) {
            if (i % 9 == 0) i += 7;
            int slot = i;
            int slotX = (8 + (slot * 18)) % 162;
            int slotY = 4 + (18 * ((int) Math.floor(slot / 9.0) + 1));
            items.put(slot, (UIItem) new UIItem(null, 0, 0, true, TEXTURE, slotX, slotY, 16, 16, 273, 84, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                if (!screenHandler.getSlot(slot).getStack().getTranslationKey().equals("block.minecraft.air")) {
                    MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(slot).getStack()), screen, m, (int) x, (int) y);
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
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(21).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);
        //Rubies
        new UIButton(TEXTURE, 79, 60, 16, 13, 189, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(22).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);
        //Discord
        new UIButton(TEXTURE, 96, 60, 16, 13, 205, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(23).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);
        //Playtime
        new UIButton(TEXTURE, 113, 60, 13, 13, 221, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(24).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);

        //Online Indicator
        onlineIndicator = (UIButton) new UIButton(TEXTURE, 116, 22, 13, 13, 234, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(25).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);

        //Visit Realm
        addSelectableElement(new UIButton(TEXTURE, 7, 79, 34, 14, 218, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(27);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(27).getStack()), screen, m, (int) x, (int) y);
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
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(29).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box));
        //Duel
        new UIButton(TEXTURE, 61, 79, 16, 14, 234, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(30);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(30).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);
        //Trade
        new UIButton(TEXTURE, 79, 79, 16, 14, 250, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(31);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(31).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);
        //Vault
        new UIButton(TEXTURE, 97, 79, 16, 14, 266, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(32);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(32).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);
        //Auctions
        addSelectableElement(new UIButton(TEXTURE, 115, 79, 16, 14, 218, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.pickupItemAtSlot(33);
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(MenuUtils.getDisplayTooltip(screenHandler.getSlot(33).getStack()), screen, m, (int) x, (int) y);
        }, false).setChildOf(box));

        //Messaging
        new UIButton(TEXTURE, 7, 5, 16, 14, 234, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
            MenuUtils.renderTooltip(List.of(Text.literal((Text.translatable("originaddons.menus.profile.messaging").getString().replaceAll("%username%", username))).setStyle(Style.EMPTY.withColor(TextColor.parse("gray").getOrThrow()))), screen, m, (int) x, (int) y);
        }, false).setChildOf(box);
        //TPA
        addSelectableElement(new UIButton(TEXTURE, 25, 5, 16, 14, 250, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/tpa " + username);
            player.closeHandledScreen();
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(List.of(Text.literal((Text.translatable("originaddons.menus.profile.tpa").getString().replaceAll("%username%", username))).setStyle(Style.EMPTY.withColor(TextColor.parse("gray").getOrThrow()))), screen, m, (int) x, (int) y);
        }, true).setChildOf(box));
        //TPAHere
        addSelectableElement(new UIButton(TEXTURE, 43, 5, 16, 14, 266, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
            MenuUtils.sendCommand(player, "/tpahere " + username);
            player.closeHandledScreen();
        }, (b, m, x, y) -> {
            MenuUtils.renderTooltip(List.of(Text.literal((Text.translatable("originaddons.menus.profile.tpahere").getString().replaceAll("%username%", username))).setStyle(Style.EMPTY.withColor(TextColor.parse("gray").getOrThrow()))), screen, m, (int) x, (int) y);
        }, true).setChildOf(box));
    }

    @Override
    public void draw(Screen screen) {
        assert MinecraftClient.getInstance().player != null;
        ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;

        if (username.equals("")) {
            Slot slot = screenHandler.slots.get(21);
            if (ItemStackUtils.getItemNBT(slot.getStack()) != null) username = Objects.requireNonNull(ItemStackUtils.getItemNBT(slot.getStack())).getCompound("SkullOwner").getString("Name");
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
        if (ItemStackUtils.getItemNBT(screenHandler.slots.get(6).getStack()) != null) {
            int status = Objects.requireNonNull(ItemStackUtils.getItemNBT(screenHandler.slots.get(6).getStack())).getInt("CustomModelData");
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
        return false;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}