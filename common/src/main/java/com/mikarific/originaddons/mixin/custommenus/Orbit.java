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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HandledScreen.class)
public class Orbit extends Screen {
    private final Window window = new Window();
    private final String MENU = "숰";

    protected Orbit(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            window.resizeWindow();
            Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/orbit.png");
            int TEXTURE_WIDTH = 316;
            int TEXTURE_HEIGHT = 112;
            UIComponent box = new UITexture(TEXTURE, (this.width - 176) / 2, (this.height - 112) / 2, 176, 112, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
            //Profile
            new UIButton(TEXTURE, 98, 12, 70, 16, 176, 0, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(8);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(8).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Quests
            new UIButton(TEXTURE, 98, 30, 70, 16, 176, 32, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(17);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(17).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Friends
            new UIButton(TEXTURE, 98, 48, 70, 16, 176, 64, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(26);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(26).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Discord
            new UIButton(TEXTURE, 98, 66, 70, 16, 246, 0, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(35);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(35).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Settings
            new UIButton(TEXTURE, 98, 84, 70, 16, 246, 32, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                CustomMenus.pickupItemAtSlot(44);
            }, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, this.getTooltipFromItem(this.client.player.currentScreenHandler.getSlot(44).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            window.draw(matrices, mouseX, mouseY);
            ci.cancel();
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            window.mouseClicked(button);
            cir.cancel();
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
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
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"), cancellable = true)
    private void mouseReleased(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointOverSlot", at = @At("HEAD"), cancellable = true)
    private void isPointOverSlot(Slot slot, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointWithinBounds", at = @At("HEAD"), cancellable = true)
    private void isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "handleHotbarKeyPressed", at = @At("HEAD"), cancellable = true)
    private void handleHotbarKeyPressed(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "onMouseClick(I)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(int button, CallbackInfo ci) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            ci.cancel();
        }
    }

    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (CustomMenus.isOrbitEnabled() && this.getTitle().getString().contains(MENU)) {
            ci.cancel();
        }
    }
}
