package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.*;
import com.mikarific.originaddons.util.CustomMenus;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(HandledScreen.class)
public class Profile extends Screen {
    private final Window window = new Window();
    private UIItem face;
    private final Map<Integer, UIItem> items = new HashMap<>();
    private UIButton onlineIndicator;
    private String username = "";
    private final String MENU = "쇉";
    private final String MENU_STAFF = "쉋";
    private final String PUNISH_CHAT = "쉎";
    private final String PUNISH_BEHAVIOR = "쉏";
    private final String PUNISH_MODS = "쉐";

    protected Profile(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF))) {
            window.resizeWindow();
            Identifier TEXTURE = null;
            int TEXTURE_WIDTH = 0;
            int TEXTURE_HEIGHT = 0;
            UIComponent box = null;
            if (this.getTitle().getString().contains(MENU)) {
                TEXTURE = new Identifier("originaddons", "gui/custommenus/profile.png");
                TEXTURE_WIDTH = 330;
                TEXTURE_HEIGHT = 110;
                box = new UITexture(TEXTURE, (this.width - 176) / 2, (this.height - 104) / 2, 176, 104, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
            } else if (this.getTitle().getString().contains(MENU_STAFF)) {
                TEXTURE = new Identifier("originaddons", "gui/custommenus/profile_staff.png");
                TEXTURE_WIDTH = 332;
                TEXTURE_HEIGHT = 142;
                box = new UITexture(TEXTURE, (this.width - 176) / 2, (this.height - 123) / 2, 176, 123, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);

                //Punish
                new UIButton(TEXTURE, 7, 96, 52, 16, 176, 110, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(36);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(36).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Teleport
                new UIButton(TEXTURE, 61, 96, 52, 16, 228, 110, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(39);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(39).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Invsee
                new UIButton(TEXTURE, 115, 96, 52, 16, 280, 110, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(42);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(42).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
            }
            assert box != null;

            //Face
            face = (UIItem) new UIItem(null, 49, 30, false, TEXTURE, 13, 28, 42, 42, 176, 0, 42, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(10).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);

            //Items
            for (int i = 7; i < 36; i++) {
                if (i % 9 == 0) i += 7;
                int slot = i;
                int slotX = (8 + (slot * 18)) % 162;
                int slotY = 4 + (18 * ((int) Math.floor(slot / 9.0) + 1));
                items.put(slot, (UIItem) new UIItem(null, 0, 0, true, TEXTURE, slotX, slotY, 16, 16, 273, 84, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    if (!this.client.player.currentScreenHandler.getSlot(slot).getStack().getTranslationKey().equals("block.minecraft.air")) {
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                    }
                }, false).setChildOf(box));
            }

            //Rank Tag
            int tag = 0;
            if (this.getTitle().getString().contains("숨")) tag = 7; //VIP
            if (this.getTitle().getString().contains("숩")) tag = 14; //PRO
            if (this.getTitle().getString().contains("숪")) tag = 21; //MVP
            if (this.getTitle().getString().contains("숫")) tag = 28; //ELITE
            if (this.getTitle().getString().contains("숬")) tag = 35; //CREATOR
            if (this.getTitle().getString().contains("숭")) tag = 42; //MOD
            if (this.getTitle().getString().contains("숮")) tag = 49; //TEAM
            if (this.getTitle().getString().contains("숯")) tag = 56; //ADMIN
            new UITexture(TEXTURE, 60, 31, 46, 7, 284, tag, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(box);
            //Alpha & Beta Tag
            if (this.getTitle().getString().contains("숝")) new UITexture(TEXTURE, 60, 41, 34, 7, 284, 63, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(box); //Alpha
            if (this.getTitle().getString().contains("숞")) new UITexture(TEXTURE, 60, 41, 34, 7, 284, 70, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(box); //Beta

            //Player Level
            new UIButton(TEXTURE, 65, 60, 13, 13, 176, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(21).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Rubies
            new UIButton(TEXTURE, 79, 60, 16, 13, 189, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(22).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Discord
            new UIButton(TEXTURE, 96, 60, 16, 13, 205, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(23).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Playtime
            new UIButton(TEXTURE, 113, 60, 13, 13, 221, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(24).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);

            //Online Indicator
            onlineIndicator = (UIButton) new UIButton(TEXTURE, 116, 22, 13, 13, 234, 84, 13, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(6).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);

            //Visit Realm
            new UIButton(TEXTURE, 7, 79, 34, 14, 218, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(27);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(27).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Friend
            int friendU = 252;
            int friendV = 0;
            if (this.getTitle().getString().contains("쉤")) friendU = 268; //Friend Remove
            if (this.getTitle().getString().contains("쉣")) {
                friendU = 218;
                friendV = 28;
            }
            new UIButton(TEXTURE, 43, 79, 16, 14, friendU, friendV, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(29);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(29).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Duel
            new UIButton(TEXTURE, 61, 79, 16, 14, 234, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(30);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(30).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Trade
            new UIButton(TEXTURE, 79, 79, 16, 14, 250, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(31);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(31).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Vault
            new UIButton(TEXTURE, 97, 79, 16, 14, 266, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(32);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(32).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Auctions
            new UIButton(TEXTURE, 115, 79, 16, 14, 218, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(33);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(33).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);

            //Messaging
            new UIButton(TEXTURE, 7, 5, 16, 14, 234, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                this.renderTooltip(m, new LiteralText((new TranslatableText("originaddons.menus.profile.messaging").getString().replaceAll("%username%", username))).setStyle(Style.EMPTY.withColor(TextColor.parse("gray"))), (int)x, (int)y);
            }, false).setChildOf(box);
            //TPA
            new UIButton(TEXTURE, 25, 5, 16, 14, 250, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                assert this.client != null;
                assert this.client.player != null;
                this.client.player.sendChatMessage("/tpa " + username);
                this.client.player.closeHandledScreen();
            }, (b, m, x, y) -> {
                this.renderTooltip(m, new LiteralText((new TranslatableText("originaddons.menus.profile.tpa").getString().replaceAll("%username%", username))).setStyle(Style.EMPTY.withColor(TextColor.parse("gray"))), (int)x, (int)y);
            }, true).setChildOf(box);
            //TPAHere
            new UIButton(TEXTURE, 43, 5, 16, 14, 266, 56, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                assert this.client != null;
                assert this.client.player != null;
                this.client.player.sendChatMessage("/tpahere " + username);
                this.client.player.closeHandledScreen();
            }, (b, m, x, y) -> {
                this.renderTooltip(m, new LiteralText((new TranslatableText("originaddons.menus.profile.tpahere").getString().replaceAll("%username%", username))).setStyle(Style.EMPTY.withColor(TextColor.parse("gray"))), (int)x, (int)y);
            }, true).setChildOf(box);
        } else if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            window.resizeWindow();
            Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/profile_punish.png");
            int TEXTURE_WIDTH = 336;
            int TEXTURE_HEIGHT = 90;
            UIComponent box = new UITexture(TEXTURE, (this.width - 176) / 2, (this.height - 55) / 2, 176, 55, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
            int chatV = 16;
            int chatVOffset = 16;
            int behaviorV = 16;
            int behaviorVOffset = 16;
            int modsV = 16;
            int modsVOffset = 16;
            int numbers = 0;
            if (this.getTitle().getString().contains(PUNISH_CHAT)) {
                chatV = 0;
                chatVOffset = 0;
                numbers = 8;
            }
            if (this.getTitle().getString().contains(PUNISH_BEHAVIOR)) {
                behaviorV = 0;
                behaviorVOffset = 0;
                numbers = 7;
            }
            if (this.getTitle().getString().contains(PUNISH_MODS)) {
                modsV = 0;
                modsVOffset = 0;
                numbers = 7;
            }
            //Chat
            new UIButton(TEXTURE, 8, 11, 53, 16, 176, chatV, chatVOffset, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(0);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(0).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Behavior
            new UIButton(TEXTURE, 61, 11, 53, 16, 229, behaviorV, behaviorVOffset, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(3);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(3).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Mods
            new UIButton(TEXTURE, 115, 11, 53, 16, 283, modsV, modsVOffset, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(6);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(6).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            for (int i = 0; i < 9; i++) {
                int numberX = 8 + (i * 18);
                int numberU = 176 + (i * 16);
                int slot = i + 9;
                if (i < numbers) {
                    new UIButton(TEXTURE, numberX, 30, 16, 14, numberU, 62, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(slot);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                } else {
                    new UIButton(TEXTURE, numberX, 30, 16, 14, numberU, 48, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(slot);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                }
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            if ((this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF))) {
                if (username.equals("")) {
                    assert this.client != null;
                    assert this.client.player != null;
                    Slot slot = this.client.player.currentScreenHandler.slots.get(21);
                    if (slot.getStack().getNbt() != null) username = Objects.requireNonNull(slot.getStack().getNbt()).getCompound("SkullOwner").getString("Name");
                }
                if (face.getStack() == null) {
                    assert this.client != null;
                    assert this.client.player != null;
                    Slot slot = this.client.player.currentScreenHandler.slots.get(21);
                    if (!slot.getStack().getTranslationKey().equals("block.minecraft.air")) face.setStack(slot.getStack());
                }
                if (items.containsKey(7) && items.get(7).getStack() == null) {
                    items.forEach((slotNumber, item) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        Slot slot = this.client.player.currentScreenHandler.slots.get(slotNumber);
                        if (!slot.getStack().getTranslationKey().equals("block.minecraft.air")) item.setStack(slot.getStack());
                    });
                }
                assert this.client != null;
                assert this.client.player != null;
                if (this.client.player.currentScreenHandler.slots.get(6).getStack().getNbt() != null) {
                    int status = Objects.requireNonNull(this.client.player.currentScreenHandler.slots.get(6).getStack().getNbt()).getInt("CustomModelData");
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
            window.draw(matrices, mouseX, mouseY);
            ci.cancel();
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            window.mouseClicked(button);
            cir.cancel();
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            if (super.keyPressed(keyCode, scanCode, modifiers)) {
                cir.setReturnValue(true);
            } else {
                assert this.client != null;
                if (this.client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
                    this.close();
                    cir.setReturnValue(true);
                }
            }
            cir.cancel();
        }
    }

    @Inject(method = "mouseDragged", at = @At("HEAD"), cancellable = true)
    private void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            cir.cancel();
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"), cancellable = true)
    private void mouseReleased(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointOverSlot", at = @At("HEAD"), cancellable = true)
    private void isPointOverSlot(Slot slot, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointWithinBounds", at = @At("HEAD"), cancellable = true)
    private void isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            cir.cancel();
        }
    }

    @Inject(method = "handleHotbarKeyPressed", at = @At("HEAD"), cancellable = true)
    private void handleHotbarKeyPressed(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            cir.cancel();
        }
    }

    @Inject(method = "onMouseClick(I)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(int button, CallbackInfo ci) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            ci.cancel();
        }
    }

    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (CustomMenus.isProfileEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(MENU_STAFF) || this.getTitle().getString().contains(PUNISH_CHAT) || this.getTitle().getString().contains(PUNISH_BEHAVIOR)  || this.getTitle().getString().contains(PUNISH_MODS))) {
            ci.cancel();
        }
    }
}
