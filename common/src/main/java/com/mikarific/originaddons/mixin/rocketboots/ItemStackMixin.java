package com.mikarific.originaddons.mixin.rocketboots;

import com.mikarific.originaddons.util.ItemBarInfo;
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
        ItemBarInfo info = ItemStackUtils.getCustomItemBar(self);

        if (info != null) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "getItemBarStep", at = @At("HEAD"), cancellable = true)
    private void customItemBarStep(CallbackInfoReturnable<Integer> cir) {
        ItemStack self = (ItemStack) (Object) this;
        ItemBarInfo info = ItemStackUtils.getCustomItemBar(self);

        if (info != null) {
            int fraction = Math.round(13.0F * info.getFraction());
            if (fraction < 0) fraction = 0;
            if (fraction > 13) fraction = 13;
            cir.setReturnValue(fraction);
            cir.cancel();
        }
    }

    @Inject(method = "getItemBarColor", at = @At("HEAD"), cancellable = true)
    private void customItemBarColor(CallbackInfoReturnable<Integer> cir) {
        ItemStack self = (ItemStack) (Object) this;
        ItemBarInfo info = ItemStackUtils.getCustomItemBar(self);

        if (info != null) {
            cir.setReturnValue(info.getColor());
            cir.cancel();
        }
    }
}