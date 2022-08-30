package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIButton;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.ui.components.UIItem;
import com.mikarific.originaddons.ui.components.UITexture;
import com.mikarific.originaddons.util.CustomMenus;
import net.minecraft.client.MinecraftClient;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mixin(HandledScreen.class)
public class Realms extends Screen {
    private final Window window = new Window();
    private final Map<Integer, UIItem> items = new HashMap<>();
    private final Map<Integer, UIButton> buttons = new HashMap<>();
    private UIComponent box;
    private final String MENU = "섩";
    private final String ROLE_SELECT = "솮";
    private final String SETTINGS = "솭";

    protected Realms(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
            window.resizeWindow();
            if (this.getTitle().getString().contains(MENU)) {
                Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/realms.png");
                int TEXTURE_WIDTH = 260;
                int TEXTURE_HEIGHT = 138;
                box = new UITexture(TEXTURE, (this.width - 176) / 2, (this.height - 138) / 2, 176, 138, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
                //Featured Realms
                if (this.getTitle().getString().contains("섪")) {
                    new UIButton(TEXTURE, 23, 15, 22, 28, 176, 44, 28, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(1);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(1).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                }
                //Teleport Home
                if (this.getTitle().getString().contains("섫")) {
                    new UIButton(TEXTURE, 58, 15, 60, 22, 176, 0, 22, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(4);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(4).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                }
                //Settings
                if (this.getTitle().getString().contains("섬")) {
                    new UIButton(TEXTURE, 131, 15, 22, 22, 236, 0, 22, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(7);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(7).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box);
                }
                //Items
                for(int i = 19; i < 44; i++) {
                    if ((i + 1) % 9 == 0) i += 2;
                    int slot = i;
                    int slotX = (8 + (slot * 18)) % 162;
                    int slotY = (18 + (18 * (int) Math.floor(slot / 9.0)));
                    items.put(slot, (UIItem) new UIItem(null, 0, 0, true, TEXTURE, slotX, slotY, 16, 16, slotX, slotY, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(slot);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        if (!this.client.player.currentScreenHandler.getSlot(slot).getStack().getTranslationKey().equals("block.minecraft.air")) {
                            this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                        }
                    }, false).setChildOf(box));
                }
                //Page Buttons
                for (int i = 46; i < 53; i += 3) {
                    int slot = i;
                    buttons.put(slot, (UIButton) new UIButton(TEXTURE, 0, 0, 0, 0, 0, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                        CustomMenus.pickupItemAtSlot(slot);
                    }, (b, m, x, y) -> {
                        assert this.client != null;
                        assert this.client.player != null;
                        this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(slot).getStack()), (int)x, (int)y);
                    }, false).setChildOf(box));
                }
            }
            if (this.getTitle().getString().contains(ROLE_SELECT)) {
                Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/realms_role_select.png");
                int TEXTURE_WIDTH = 288;
                int TEXTURE_HEIGHT = 52;
                box = new UITexture(TEXTURE, (this.width - 176) / 2, (this.height - 52) / 2, 176, 52, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
                //Resident
                new UIButton(TEXTURE, 24, 17, 56, 18, 176, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(2);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(2).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Trusted
                new UIButton(TEXTURE, 98, 17, 56, 18, 232, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(6);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(6).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
            }
            if (this.getTitle().getString().contains(SETTINGS)) {
                Identifier TEXTURE = new Identifier("originaddons", "gui/custommenus/realms_settings.png");
                int TEXTURE_WIDTH = 266;
                int TEXTURE_HEIGHT = 52;
                box = new UITexture(TEXTURE, (this.width - 176) / 2, (this.height - 52) / 2 - 26, 176, 52, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setChildOf(window);
                //Realm Members
                new UIButton(TEXTURE, 25, 17, 36, 18, 176, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(1);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(1).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Reset Realm
                new UIButton(TEXTURE, 79, 17, 18, 18, 212, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(4);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(4).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
                //Visiting Rules
                new UIButton(TEXTURE, 115, 17, 36, 18, 230, 0, 18, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    CustomMenus.pickupItemAtSlot(6);
                }, (b, m, x, y) -> {
                    assert this.client != null;
                    assert this.client.player != null;
                    this.renderTooltip(m, CustomMenus.getDisplayTooltip(this.client.player.currentScreenHandler.getSlot(6).getStack()), (int)x, (int)y);
                }, false).setChildOf(box);
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS)) {
            if (this.getTitle().getString().contains(MENU)) {
                if (CustomMenus.getTeleportingHome()) {
                    CustomMenus.pickupItemAtSlot(3);
                    CustomMenus.setTeleportingHome(false);
                    assert MinecraftClient.getInstance().player != null;
                    MinecraftClient.getInstance().player.closeHandledScreen();
                    ci.cancel();
                } else if (CustomMenus.isRealmsEnabled()) {
                    for (int i = 46; i < 53; i += 3) {
                        int slot = i;
                        assert this.client != null;
                        assert this.client.player != null;
                        if (this.client.player.currentScreenHandler.getSlot(slot).getStack().getNbt() != null) {
                            int customModelData = Objects.requireNonNull(this.client.player.currentScreenHandler.getSlot(slot).getStack().getNbt()).getInt("CustomModelData");
                            //Back Button
                            if (customModelData == 8009) {
                                buttons.get(slot).setX(box.getX() + 26);
                                buttons.get(slot).setY(box.getY() + 111);
                                buttons.get(slot).setWidth(16);
                                buttons.get(slot).setHeight(10);
                                buttons.get(slot).setU(198);
                                buttons.get(slot).setV(44);
                                buttons.get(slot).setHoveredVOffset(10);
                            } else if (customModelData == 8010) {
                                buttons.get(slot).setX(box.getX() + 26);
                                buttons.get(slot).setY(box.getY() + 111);
                                buttons.get(slot).setWidth(16);
                                buttons.get(slot).setHeight(10);
                                buttons.get(slot).setU(198);
                                buttons.get(slot).setV(64);
                                buttons.get(slot).setHoveredVOffset(0);
                            }
                            //Members Only
                            if (customModelData == 8015) {
                                buttons.get(slot).setX(box.getX() + 80);
                                buttons.get(slot).setY(box.getY() + 108);
                                buttons.get(slot).setWidth(15);
                                buttons.get(slot).setHeight(16);
                                buttons.get(slot).setU(245);
                                buttons.get(slot).setV(44);
                                buttons.get(slot).setHoveredVOffset(16);
                            } else if (customModelData == 8016) {
                                buttons.get(slot).setX(box.getX() + 80);
                                buttons.get(slot).setY(box.getY() + 108);
                                buttons.get(slot).setWidth(15);
                                buttons.get(slot).setHeight(16);
                                buttons.get(slot).setU(230);
                                buttons.get(slot).setV(44);
                                buttons.get(slot).setHoveredVOffset(16);
                            }
                            //Next Button
                            if (customModelData == 8007) {
                                buttons.get(slot).setX(box.getX() + 134);
                                buttons.get(slot).setY(box.getY() + 111);
                                buttons.get(slot).setWidth(16);
                                buttons.get(slot).setHeight(10);
                                buttons.get(slot).setU(214);
                                buttons.get(slot).setV(44);
                                buttons.get(slot).setHoveredVOffset(10);
                            } else if (customModelData == 8008) {
                                buttons.get(slot).setX(box.getX() + 134);
                                buttons.get(slot).setY(box.getY() + 111);
                                buttons.get(slot).setWidth(16);
                                buttons.get(slot).setHeight(10);
                                buttons.get(slot).setU(214);
                                buttons.get(slot).setV(64);
                                buttons.get(slot).setHoveredVOffset(0);
                            }
                        }
                    }
                    assert this.client != null;
                    assert this.client.player != null;
                    if (items.containsKey(19) && items.get(19).getStack() != this.client.player.currentScreenHandler.slots.get(19).getStack()) {
                        items.forEach((slotNumber, item) -> {
                            Slot slot = this.client.player.currentScreenHandler.slots.get(slotNumber);
                            if (!slot.getStack().getTranslationKey().equals("block.minecraft.air")) {
                                item.setStack(slot.getStack());
                            } else {
                                item.setStack(null);
                            }
                        });
                    }
                }
            }
            if (CustomMenus.isRealmsEnabled()) {
                window.draw(matrices, mouseX, mouseY);
                ci.cancel();
            }
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
            window.mouseClicked(button);
            cir.cancel();
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
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
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
            cir.cancel();
        }
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"), cancellable = true)
    private void mouseReleased(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointOverSlot", at = @At("HEAD"), cancellable = true)
    private void isPointOverSlot(Slot slot, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
            cir.cancel();
        }
    }

    @Inject(method = "isPointWithinBounds", at = @At("HEAD"), cancellable = true)
    private void isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
            cir.cancel();
        }
    }

    @Inject(method = "handleHotbarKeyPressed", at = @At("HEAD"), cancellable = true)
    private void handleHotbarKeyPressed(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
            cir.cancel();
        }
    }

    @Inject(method = "onMouseClick(I)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(int button, CallbackInfo ci) {
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
            ci.cancel();
        }
    }

    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("HEAD"), cancellable = true)
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (CustomMenus.isRealmsEnabled() && (this.getTitle().getString().contains(MENU) || this.getTitle().getString().contains(ROLE_SELECT) || this.getTitle().getString().contains(SETTINGS))) {
            ci.cancel();
        }
    }
}
