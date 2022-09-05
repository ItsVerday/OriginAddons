package com.mikarific.originaddons.mixin.rocketbootsfuelbar;

import com.mikarific.originaddons.util.ItemStackUtils;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "isItemBarVisible", at = @At("HEAD"), cancellable = true)
    private void customItemBarVisibility(CallbackInfoReturnable<Boolean> cir) {
        ItemStack self = (ItemStack) (Object) this;

        if (ItemStackUtils.hasCustomItemBar(self)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "getItemBarStep", at = @At("HEAD"), cancellable = true)
    private void customItemBarStep(CallbackInfoReturnable<Integer> cir) {
        ItemStack self = (ItemStack) (Object) this;

        if (ItemStackUtils.hasCustomItemBar(self)) {
            cir.setReturnValue(ItemStackUtils.getCustomItemBarStep(self));
            cir.cancel();
        }
    }

    @Inject(method = "getItemBarColor", at = @At("HEAD"), cancellable = true)
    private void customItemBarColor(CallbackInfoReturnable<Integer> cir) {
        ItemStack self = (ItemStack) (Object) this;

        if (ItemStackUtils.hasCustomItemBar(self)) {
            cir.setReturnValue(ItemStackUtils.getCustomItemBarColor(self));
            cir.cancel();
        }
    }
}