package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.*;
import com.mikarific.originaddons.util.CustomMenus;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(HandledScreen.class)
public class Gestures extends Screen {
    private final Window window = new Window();

    private final Map<Integer, UIItem> items = new HashMap<>();
    private final String FAVORITES_MENU = "쉂";
    private final String ALL_MENU = "쉃";

    protected Gestures(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            window.resizeWindow();
            if (this.getTitle().getString().contains(FAVORITES_MENU)) {
                Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/gestures_favorites.png");
                int TEXTURE_WIDTH = 384;
                int TEXTURE_HEIGHT = 136;
                UIComponent box = new UITexture(TEXTURE, (this.width - 176) / 2, (this.height - 106) / 2, 176, 106, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
                //Slot 1
                new UIButton(TEXTURE, 8, 27, 52, 34, 176, 0, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(9);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(9).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Slot 2
                new UIButton(TEXTURE, 62, 27, 52, 34, 228, 0, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(12);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(12).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Slot 3
                new UIButton(TEXTURE, 116, 27, 52, 34, 280, 0, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(15);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(15).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Slot 4
                new UIButton(TEXTURE, 8, 63, 52, 34, 176, 68, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(27);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(27).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Slot 5
                new UIButton(TEXTURE, 62, 63, 52, 34, 228, 68, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(30);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(30).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Slot 6
                new UIButton(TEXTURE, 116, 63, 52, 34, 280, 68, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(33);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(33).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);

                //Back
                new UIButton(TEXTURE, 8, 9, 16, 14, 332, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(0);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(0).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //View All
                new UIButton(TEXTURE, 116, 9, 52, 14, 332, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(6);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(6).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Gestures
                List<Text> gestures = this.title.getSiblings().get(1).getSiblings().get(0).getSiblings().stream().filter(sibling -> sibling.getStyle().getFont().getPath().contains("gesture")).toList();
                for (int i = 0; i < gestures.size(); i++) {
                    if (i == 0) new UIText(gestures.get(i), 16777215, 18, -3).setChildOf(box);
                    if (i == 1) new UIText(gestures.get(i), 16777215, 73, -3).setChildOf(box);
                    if (i == 2) new UIText(gestures.get(i), 16777215, 128, -3).setChildOf(box);
                    if (i == 3) new UIText(gestures.get(i), 16777215, 21, -3).setChildOf(box);
                    if (i == 4) new UIText(gestures.get(i), 16777215, 76, -3).setChildOf(box);
                    if (i == 5) new UIText(gestures.get(i), 16777215, 131, -3).setChildOf(box);
                }
            }
            if (this.getTitle().getString().contains(ALL_MENU)) {
                Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/gestures_all.png");
                int TEXTURE_WIDTH = 248;
                int TEXTURE_HEIGHT = 124;
                UIComponent box = new UITexture(TEXTURE, (this.width - 176) / 2, (this.height - 124) / 2, 176, 124, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
                //Back
                if (this.title.getString().contains("쉄")) {
                    new UIButton(TEXTURE, 8, 9, 16, 14, 192, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(0);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(0).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                } else {
                    new UIButton(TEXTURE, 8, 9, 16, 14, 176, 0, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(0);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(0).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                }
                //Previous Page
                if (this.title.getString().contains("쉅")) {
                    new UIButton(TEXTURE, 8, 101, 36, 14, 176, 56, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(45).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                } else {
                    new UIButton(TEXTURE, 8, 101, 36, 14, 176, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(45);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(45).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                }
                //Next Page
                if (this.title.getString().contains("쉆")) {
                    new UIButton(TEXTURE, 132, 101, 36, 14, 212, 56, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {}, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(52).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                } else {
                    new UIButton(TEXTURE, 132, 101, 36, 14, 212, 28, 14, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(52);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(52).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                }
                //Page Numbers
                new UIText(new LiteralText(this.title.getSiblings().get(1).getSiblings().get(0).getString()).setStyle(Style.EMPTY), 16777215, 80, 104).setChildOf(box);
                //Slot Buttons
                for (int i = 9; i < 45; i++) {
                    int slot = i;
                    int slotX = (8 + (slot * 18)) % 162;
                    int slotY = 9 + (18 * (int) Math.floor(slot / 9.0));
                    new UIItem(null, 0, 0, true, TEXTURE, slotX, slotY, 16, 16, slotX, slotY, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(slot);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        if (!this.client.player.currentScreenHandler.getSlot(slot).getStack().getTranslationKey().equals("block.minecraft.air")) {
                            this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                        }
                    }, false).setChildOf(box);
                }
                //Slots
                new UIText(this.title.getSiblings().get(0).getSiblings().get(1), 16777215, 8, -3).setChildOf(box);
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            window.draw(matrices, mouseX, mouseY);
            ci.cancel();
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            window.mouseClicked(button);
            cir.cancel();
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
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
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            cir.cancel();
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"), cancellable = true)
    private void mouseReleased(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointOverSlot", at = @At("HEAD"), cancellable = true)
    private void isPointOverSlot(Slot slot, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointWithinBounds", at = @At("HEAD"), cancellable = true)
    private void isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            cir.cancel();
        }
    }

    @Inject(method = "handleHotbarKeyPressed", at = @At("HEAD"), cancellable = true)
    private void handleHotbarKeyPressed(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            cir.cancel();
        }
    }

    @Inject(method = "onMouseClick(I)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(int button, CallbackInfo ci) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            ci.cancel();
        }
    }

    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (CustomMenus.isGesturesEnabled() && (this.getTitle().getString().contains(FAVORITES_MENU) || this.getTitle().getString().contains(ALL_MENU))) {
            ci.cancel();
        }
    }
}
