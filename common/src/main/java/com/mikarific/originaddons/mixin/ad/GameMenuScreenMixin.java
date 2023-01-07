package com.mikarific.originaddons.mixin.ad;

import com.mikarific.originaddons.OriginAddons;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin {
    @ModifyArg(
            method = "initWidgets()V",
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=menu.shareToLan"
                    ),
                    to = @At(
                            value = "CONSTANT",
                            args = "stringValue=menu.returnToMenu"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V"
            ),
            index = 4
    )
    private Text changeOpenToLanText(Text text) {
        if (OriginAddons.onOriginRealms()) return Text.translatable("originaddons.ad");
        return text;
    }
}
