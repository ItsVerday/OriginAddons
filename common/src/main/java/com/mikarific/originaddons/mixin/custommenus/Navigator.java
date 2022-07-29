package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.CustomMenus;
import net.minecraft.client.gui.screen.ConfirmChatLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HandledScreen.class)
public abstract class Navigator extends Screen {
    @Shadow public abstract boolean mouseClicked(double mouseX, double mouseY, int button);

    @Shadow protected int x;
    @Shadow protected int y;
    private final Window window = new Window();
    private final String MENU = "섥";

    protected Navigator(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            window.resizeWindow();
            Identifier MAIN_TEXTURE = new Identifier("originaddons", "gui/custommenus/navigator.png");
            String MAIN_MENU = "섦";
            String BALLOON_MENU = "솝";
            int MAIN_TEXTURE_WIDTH = 404;
            int MAIN_TEXTURE_HEIGHT = 141;
            if (this.getTitle().getString().contains(MAIN_MENU)) {
                UIComponent box = new UITexture(MAIN_TEXTURE, (this.width - 176) / 2, (this.height - 141) / 2, 176, 141, 0, 0, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT).setChildOf(window);
                //Spawn
                new UIButton(MAIN_TEXTURE, 15, 41, 38, 38, 176, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(0);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(0).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Realms
                new UIButton(MAIN_TEXTURE, 69, 41, 38, 38, 214, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(3);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(3).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Resource Worlds
                new UIButton(MAIN_TEXTURE, 123, 41, 38, 38, 252, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(6);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(6).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Homes
                new UIButton(MAIN_TEXTURE, 15, 95, 38, 38, 290, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(27);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(27).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Towns
                new UIButton(MAIN_TEXTURE, 69, 95, 38, 38, 328, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(30);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(30).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Adventure Worlds
                new UIButton(MAIN_TEXTURE, 123, 95, 38, 38, 366, 0, 38, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(33);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(33).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);

                //Teleport Home
                new UIButton(MAIN_TEXTURE, 76, 79, 24, 12, 291, 97, 12, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    CustomMenus.setTeleportingHome(true);
                    CustomMenus.pickupItemAtSlot(3);
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.teleporthome.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.teleporthome.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, false).setChildOf(box);

                //Auction House
                new UIButton(MAIN_TEXTURE, 15, 17, 38, 19, 176, 96, 19, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.client.player.sendChatMessage("/ah");
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.auctionhouse.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.auctionhouse.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);
                //Badges
                new UIButton(MAIN_TEXTURE, 64, 17, 19, 19, 214, 96, 19, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.client.player.sendChatMessage("/badges");
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.badges.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.badges.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);
                //Gestures
                new UIButton(MAIN_TEXTURE, 93, 17, 19, 19, 233, 96, 19, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.client.player.sendChatMessage("/g");
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.gestures.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.gestures.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);
                //Messaging
                new UIButton(MAIN_TEXTURE, 123, 17, 38, 19, 252, 96, 19, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.messaging.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.messaging.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);

                //Profile
                new UIButton(MAIN_TEXTURE, 15, 3, 24, 10, 176, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.client.player.sendChatMessage("/profile");
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.profile.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.profile.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);
                //Quests
                new UIButton(MAIN_TEXTURE, 39, 3, 25, 10, 200, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.client.player.sendChatMessage("/quests");
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.quests.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.quests.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);
                //Friends
                new UIButton(MAIN_TEXTURE, 64, 3, 24, 10, 225, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.client.player.sendChatMessage("/friends");
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.friends.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.friends.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);
                //Discord
                new UIButton(MAIN_TEXTURE, 88, 3, 24, 10, 249, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    assert this.client != null;
                    this.client.setScreen(new ConfirmChatLinkScreen((confirmed) -> {
                        if (confirmed) {
                            Util.getOperatingSystem().open("https://discord.gg/MHRhtddvRW");
                        }
                        this.client.setScreen(this);
                    }, "https://discord.gg/MHRhtddvRW", true));
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.discord.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.discord.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);
                //Settings
                new UIButton(MAIN_TEXTURE, 112, 3, 25, 10, 273, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.client.player.sendChatMessage("/settings");
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.settings.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.settings.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);
                //Wiki
                new UIButton(MAIN_TEXTURE, 137, 3, 24, 10, 298, 76, 10, MAIN_TEXTURE_WIDTH, MAIN_TEXTURE_HEIGHT, () -> {
                    assert this.client != null;
                    this.client.setScreen(new ConfirmChatLinkScreen((confirmed) -> {
                        if (confirmed) {
                            Util.getOperatingSystem().open("https://originrealms.wiki/");
                        }
                        this.client.setScreen(this);
                    }, "https://originrealms.wiki/", true));
                }, (b, m, x, y) -> {
                    this.renderTooltip(m, List.of(new TranslatableText("originaddons.menus.navigator.wiki.title").setStyle(Style.EMPTY.withColor(TextColor.parse("gold")).withBold(true)), new TranslatableText("originaddons.menus.navigator.wiki.description").setStyle(Style.EMPTY.withColor(TextColor.parse("gray")))), (int)x, (int)y);
                }, true).setChildOf(box);
            } else if (this.getTitle().getString().contains(BALLOON_MENU)) {
                Identifier BALLOON_TEXTURE = new Identifier("originaddons", "gui/custommenus/navigator_balloon.png");
                int BALLOON_TEXTURE_WIDTH = 306;
                int BALLOON_TEXTURE_HEIGHT = 152;
                UIComponent box = new UITexture(BALLOON_TEXTURE, (this.width - 176) / 2, (this.height - 124) / 2, 176, 124, 0, 0, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT).setChildOf(window);
                //Red Balloon
                new UIButton(BALLOON_TEXTURE, 15, 16, 65, 38, 176, 0, 38, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(0);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(0).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Yellow Balloon
                new UIButton(BALLOON_TEXTURE, 96, 16, 65, 38, 241, 0, 38, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(5);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(5).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                new UIButton(BALLOON_TEXTURE, 15, 70, 65, 38, 176, 76, 38, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(27);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(27).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                new UIButton(BALLOON_TEXTURE, 96, 70, 65, 38, 241, 76, 38, BALLOON_TEXTURE_WIDTH, BALLOON_TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(32);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(32).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            window.draw(matrices, mouseX, mouseY);
            ci.cancel();
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            window.mouseClicked(button);
            cir.cancel();
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
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
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"), cancellable = true)
    private void mouseReleased(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointOverSlot", at = @At("HEAD"), cancellable = true)
    private void isPointOverSlot(Slot slot, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointWithinBounds", at = @At("HEAD"), cancellable = true)
    private void isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "handleHotbarKeyPressed", at = @At("HEAD"), cancellable = true)
    private void handleHotbarKeyPressed(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "onMouseClick(I)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(int button, CallbackInfo ci) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            ci.cancel();
        }
    }

    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (CustomMenus.isNavigatorEnabled() && this.getTitle().getString().contains(MENU)) {
            ci.cancel();
        }
    }
}