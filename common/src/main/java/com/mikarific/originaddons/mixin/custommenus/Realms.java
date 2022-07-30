package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.ui.Window;
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
public class Realms extends Screen {
    private final String MENU = "섩";

    protected Realms(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (this.getTitle().getString().contains(MENU)) {
            if (CustomMenus.getTeleportingHome()) {
                CustomMenus.pickupItemAtSlot(3);
                CustomMenus.setTeleportingHome(false);
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (this.getTitle().getString().contains(MENU)) {
            if (CustomMenus.getTeleportingHome()) {
                CustomMenus.pickupItemAtSlot(3);
                CustomMenus.setTeleportingHome(false);
                ci.cancel();
            }
        }
    }
}
