package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.util.custommenus.CustomMenus;
import com.mikarific.originaddons.util.custommenus.screens.*;
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
public abstract class HandledScreenMixin extends Screen {

    @Shadow protected abstract void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY);

    private final Window window = new Window();

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        initWindow();
    }

    private void initWindow() {
        if (CustomMenus.isEnabled(this)) {
            window.resizeWindow();
            if (CustomMenus.isBadges(this)) Badges.init(this, window);
            if (CustomMenus.isGesturesFavorites(this)) GesturesFavorites.init(this, window);
            if (CustomMenus.isGesturesAll(this)) GesturesAll.init(this, window);
            if (CustomMenus.isNavigator(this)) Navigator.init(this, window);
            if (CustomMenus.isOrbit(this)) Orbit.init(this, window);
            if (CustomMenus.isPainting(this)) Painting.init(this, window);
            if (CustomMenus.isProfile(this)) Profile.init(this, window);
            if (CustomMenus.isProfileStaff(this)) ProfileStaff.init(this, window);
            if (CustomMenus.isProfilePunish(this)) ProfilePunish.init(this, window);
            if (CustomMenus.isRealms(this)) Realms.init(this, window);
            if (CustomMenus.isRealmsRoleSelect(this)) RealmsRoleSelect.init(this, window);
            if (CustomMenus.isRealmsSettings(this)) RealmsSettings.init(this, window);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/screen/ingame/HandledScreen.drawBackground(Lnet/minecraft/client/util/math/MatrixStack;FII)V"))
    private void drawBackground(HandledScreen instance, MatrixStack matrixStack, float delta, int mouseX, int mouseY) {
        if (CustomMenus.isEnabled(this)) {
            window.draw(matrixStack, mouseX, mouseY);
            if (CustomMenus.isBadges(this)) Badges.draw(this);
            if (CustomMenus.isPainting(this)) Painting.draw(this);
            if (CustomMenus.isProfile(this)) Profile.draw(this);
            if (CustomMenus.isProfileStaff(this)) ProfileStaff.draw(this);
            if (CustomMenus.isRealms(this)) Realms.draw(this);
        } else {
            drawBackground(matrixStack, delta, mouseX, mouseY);
        }
    }

    @Inject(method = "drawForeground", at = @At("HEAD"), cancellable = true)
    private void drawForeground(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo ci) {
        if (CustomMenus.isEnabled(this)) {
            ci.cancel();
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.isEnabled(this)) {
            window.mouseClicked(button, cir);
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (CustomMenus.isRealms(this)) {
            if (CustomMenus.getTeleportingHome()) {
                CustomMenus.pickupItemAtSlot(3);
                CustomMenus.setTeleportingHome(false);
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.closeHandledScreen();
                ci.cancel();
            }
        }
    }
}
