package com.mikarific.originaddons.mixin.emojipicker;

import com.mikarific.originaddons.util.emojipicker.EmojiPicker;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLoginNetworkHandler.class)
public class ClientLoginNetworkHandlerMixin {
    @Inject(method = "onSuccess", at = @At("HEAD"))
    private void playerLoginSuccess(CallbackInfo ci) {
        EmojiPicker.clearUnlocked();
    }
}
