package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIItem;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.CustomMenus;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public class Badges extends Screen {
    private final Window window = new Window();
    private UIItem face;
    private final String MENU = "솷";

    protected Badges(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            window.resizeWindow();
            Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/badges.png");
            int TEXTURE_WIDTH = 260;
            int TEXTURE_HEIGHT = 99;
            UIComponent box = new UITexture(TEXTURE, (this.width - 196) / 2, (this.height - 99) / 2, 196, 99, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
            //Face
            face = (UIItem) new UIItem(null, 6, 6, false, TEXTURE, 84, 25, 28, 28, 196, 0, 28, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(4).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Farming
            new UIButton(TEXTURE, 36, 67, 16, 16, 196, 56, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(19).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Combat
            new UIButton(TEXTURE, 72, 67, 16, 16, 212, 56, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(21).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Exploration
            new UIButton(TEXTURE, 108, 67, 16, 16, 228, 56, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(23).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
            //Magic
            new UIButton(TEXTURE, 144, 67, 16, 16, 244, 56, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                assert this.client != null;
                assert this.client.player != null;
                this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(25).getStack()), (int)x, (int)y);
            }, false).setChildOf(box);
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            if (face.getStack() == null) {
                assert this.client != null;
                assert this.client.player != null;
                Slot slot = this.client.player.currentScreenHandler.slots.get(4);
                if (!slot.getStack().getTranslationKey().equals("block.minecraft.air")) face.setStack(slot.getStack());
            }
            window.draw(matrices, mouseX, mouseY);
            ci.cancel();
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            window.mouseClicked(button);
            cir.cancel();
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
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
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"), cancellable = true)
    private void mouseReleased(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointOverSlot", at = @At("HEAD"), cancellable = true)
    private void isPointOverSlot(Slot slot, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointWithinBounds", at = @At("HEAD"), cancellable = true)
    private void isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "handleHotbarKeyPressed", at = @At("HEAD"), cancellable = true)
    private void handleHotbarKeyPressed(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            cir.cancel();
        }
    }

    @Inject(method = "onMouseClick(I)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(int button, CallbackInfo ci) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            ci.cancel();
        }
    }

    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (CustomMenus.isBadgeEnabled() && this.getTitle().getString().contains(MENU)) {
            ci.cancel();
        }
    }
}
