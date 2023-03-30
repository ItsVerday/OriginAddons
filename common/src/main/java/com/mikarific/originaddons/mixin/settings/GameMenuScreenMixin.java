package com.mikarific.originaddons.mixin.settings;

import com.mikarific.originaddons.OriginAddons;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets()V", at = @At("TAIL"))
    private void addSettingsButton(CallbackInfo ci) {
        if (OriginAddons.onOriginRealms()) {
            this.addDrawableChild(new TexturedButtonWidget(this.width / 2 - 126, this.height / 4 + 72 + -16, 20, 20, 0, 0, 20, new Identifier("originaddons", "gui/settings.png"), 20, 40, (button) -> {
                assert this.client != null;
                this.client.setScreen(OriginAddons.getConfigScreen(this));
            }));
        }
    }
}