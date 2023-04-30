package com.mikarific.originaddons.mixin.other;

import com.mikarific.originaddons.util.Other;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseScroll(JDD)V", at = @At("HEAD"))
    private void trackWheel (long window, double horizontal, double vertical, CallbackInfo info) {
        if (vertical > 2.0) vertical = 2.0;
        if (vertical < -2.0) vertical = -2.0;
        Other.changeScrollOffset(vertical);
    }
}