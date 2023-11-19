package com.mikarific.originaddons.mixin.custommenus;

import com.mikarific.originaddons.util.MenuUtils;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Screen.class)
public class ScreenMixin {
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
}
