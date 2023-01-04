package com.mikarific.originaddons.mixin.emojipicker;

import com.mikarific.originaddons.ui.Window;
import com.mikarific.originaddons.ui.components.*;
import com.mikarific.originaddons.util.emojipicker.EmojiInstance;
import com.mikarific.originaddons.util.emojipicker.EmojiPicker;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {
    private static final Style EMOJI_TOKEN_HOVER_STYLE = Style.EMPTY.withColor(TextColor.parse("gray"));

    @Shadow protected TextFieldWidget chatField;

    private final Window window = new Window();
    private final Identifier TEXTURE = new Identifier("originaddons", "gui/emojipicker/emojipicker.png");
    private UIComponent box;

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        if (EmojiPicker.isEmojiPickerEnabled()) {
            window.resizeWindow();
            int TEXTURE_WIDTH = 158;
            int TEXTURE_HEIGHT = 75;
            box = new UITexture(TEXTURE, 4, this.height - 75 - 34, 122, 75, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT).setVisible(false).setChildOf(window);
            new UIButton(TEXTURE, 4, this.height - 16 - 15, 16, 16, 122, 0, 16, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                box.setVisible(!box.isVisible());
            }, true).setChildOf(window);
            UIComponent scrollable = new UIScrollable(7, 8, 108, 61).setChildOf(box);
            int emojiX = 0;
            int emojiY = 0;
            for (EmojiInstance emoji: EmojiPicker.getEmojis()) {
                if (!emoji.isUnlocked()) continue;

                if (emoji.getInfo().getWidth() + emojiX > 9) {
                    emojiX = 0;
                    emojiY++;
                }

                new UIEmoji(emoji, TEXTURE, emojiX * 12, emojiY * 12, 122, 32, 12, TEXTURE_WIDTH, TEXTURE_HEIGHT, () -> {
                    int cursor = chatField.getCursor();
                    String chatText = chatField.getText();
                    String beforeEmoji = chatText.substring(0, cursor);
                    String afterEmoji = chatText.substring(cursor);

                    if (!beforeEmoji.equals("") && !beforeEmoji.endsWith(" ")) {
                        beforeEmoji = beforeEmoji + " ";
                        cursor++;
                    }

                    if (!afterEmoji.startsWith(" ")) {
                        afterEmoji = " " + afterEmoji;
                        cursor++;
                    }

                    chatField.setText(beforeEmoji + emoji.getInfo().getToken() + afterEmoji);
                    chatField.setCursor(cursor + emoji.getInfo().getToken().length());
                }, (b, m, x, y) -> {
                    renderTooltip(m, new LiteralText(emoji.getInfo().getToken()).setStyle(EMOJI_TOKEN_HOVER_STYLE), (int) x, (int) y);
                }, true).setChildOf(scrollable);

                emojiX += emoji.getInfo().getWidth();
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (EmojiPicker.isEmojiPickerEnabled()) {
            window.draw(matrices, mouseX, mouseY);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (EmojiPicker.isEmojiPickerEnabled()) {
            window.mouseClicked(button, cir);
        }
    }

    @Inject(method = "mouseScrolled(DDD)Z", at = @At("HEAD"), cancellable = true)
    private void scrollWindow(double mouseX, double mouseY, double amount, CallbackInfoReturnable<Boolean> cir) {
        if (EmojiPicker.isEmojiPickerEnabled() && box.isVisible() && box.isHovered()) cir.setReturnValue(true);
    }

    @Inject(method = "mouseClicked(DDI)Z", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/hud/InGameHud.getChatHud ()Lnet/minecraft/client/gui/hud/ChatHud;"), cancellable = true)
    private void disableHudClicks(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (EmojiPicker.isEmojiPickerEnabled() && box.isVisible() && box.isHovered()) cir.setReturnValue(false);
    }

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/screen/ChatScreen.renderTextHoverEffect (Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Style;II)V"), cancellable = true)
    private void disableHudTooltips(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (EmojiPicker.isEmojiPickerEnabled() && box.isVisible() && box.isHovered()) ci.cancel();
    }
}
