package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.menu.CustomMenus;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {
    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "drawForeground", at = @At("HEAD"), cancellable = true)
    private void drawForeground(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        if (CustomMenus.getCurrentMenu() != null) {
            ci.cancel();
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (CustomMenus.getCurrentMenu() != null) {
            UIComponent clickedElement = MenuUtils.window.mouseClicked(button, cir);
            CustomMenus.getCurrentMenu().mouseClicked(this, MenuUtils.window, clickedElement);
        }
    }
}