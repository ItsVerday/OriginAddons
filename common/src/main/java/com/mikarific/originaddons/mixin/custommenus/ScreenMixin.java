package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.menu.CustomMenu;
import com.mikarific.originaddons.menu.CustomMenus;
import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.UIComponent;
import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow public abstract void renderBackground(DrawContext context, int mouseX, int mouseY, float delta);

    @ModifyArg(
            method = "renderWithTooltip",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Lnet/minecraft/client/gui/tooltip/TooltipPositioner;II)V"),
            index = 3
    )
    private int modifyTooltipX(int x) {
        return (int) MenuUtils.getTooltipX(x);
    }

    @ModifyArg(
            method = "renderWithTooltip",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Lnet/minecraft/client/gui/tooltip/TooltipPositioner;II)V"),
            index = 4
    )
    private int modifyTooltipY(int y) {
        return (int) MenuUtils.getTooltipY(y);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;renderBackground(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    private void drawBackground(Screen instance, DrawContext context, int mouseX, int mouseY, float delta) {
        Screen self = (Screen) (Object) this;
        MenuUtils.setForcedTooltip(false, 0, 0);
        if (CustomMenus.getCurrentMenu() != null && self instanceof HandledScreen<?>) {
            CustomMenus.getCurrentMenu().doDraw(self, mouseX, mouseY);
            MenuUtils.window.draw(context, mouseX, mouseY, CustomMenus.getCurrentMenu().isRenderSelectedTooltip());
            CustomMenus.getCurrentMenu().drawSelectedElementTooltip(context);
        } else {
            renderBackground(context, mouseX, mouseY, delta);
        }
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        MenuUtils.initMenu((Screen) (Object) this);
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void onClose(CallbackInfo ci) {
        MenuUtils.clearCurrentMenu((Screen) (Object) this);
    }

    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void tabPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode != 258 && keyCode != 257) return;

        if (CustomMenus.getCurrentMenu() != null) {
            if (keyCode == 258) {
                CustomMenus.getCurrentMenu().selectNextElement(Screen.hasShiftDown());
            } else {
                CustomMenus.getCurrentMenu().clickSelectedElement(Screen.hasShiftDown() ? 1 : 0);
            }
        }
    }
}
