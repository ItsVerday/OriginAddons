package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.menu.CustomMenus;
import com.mikarific.originaddons.ui.Window;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixinV2 extends Screen {
    @Shadow protected abstract void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY);

    private static CustomMenu currentMenu = null;
    private static Window window = null;

    protected HandledScreenMixinV2(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        initMenu(this);
    }

    private void setCurrentMenu(CustomMenu menu, Screen screen) {
        currentMenu = menu;
        window = new Window();
        menu.doInit(screen, window);
    }

    private void clearCurrentMenu() {
        if (currentMenu != null) currentMenu.close(this);
        currentMenu = null;
        window = null;
    }

    private void initMenu(Screen screen) {
        CustomMenu menu = CustomMenus.getMenuForScreen(screen);
        if (menu == null) {
            clearCurrentMenu();
            return;
        }

        if (currentMenu == null || !currentMenu.equals(menu)) {
            clearCurrentMenu();
            setCurrentMenu(menu, screen);
        }
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void onClose(CallbackInfo ci) {
        clearCurrentMenu();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/screen/ingame/HandledScreen.drawBackground(Lnet/minecraft/client/util/math/MatrixStack;FII)V"))
    private void drawBackground(HandledScreen instance, MatrixStack matrixStack, float delta, int mouseX, int mouseY) {
        if (currentMenu != null) {
            window.draw(matrixStack, mouseX, mouseY);
            currentMenu.doDraw(this, matrixStack);
        } else {
            drawBackground(matrixStack, delta, mouseX, mouseY);
        }
    }

    @Inject(method = "drawForeground", at = @At("HEAD"), cancellable = true)
    private void drawForeground(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo ci) {
        if (currentMenu != null) {
            ci.cancel();
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (currentMenu != null) {
            window.mouseClicked(button, cir);
            currentMenu.mouseClicked(cir);
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (currentMenu != null) {
            if (com.mikarific.originaddons.util.custommenus.CustomMenus.getTeleportingHome()) {
                com.mikarific.originaddons.util.custommenus.CustomMenus.pickupItemAtSlot(3);
                com.mikarific.originaddons.util.custommenus.CustomMenus.setTeleportingHome(false);
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.closeHandledScreen();
                ci.cancel();
            }
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void tabPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode != 258 && keyCode != 257) return;

        if (currentMenu != null) {
            if (keyCode == 258) {
                currentMenu.selectNextElement();
            } else {
                currentMenu.clickSelectedElement();
            }
        }
    }
}