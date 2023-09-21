package com.mikarific.originaddons.mixin.tooltip;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.ItemStackUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract String toString();

    @Inject(at = @At("RETURN"), method = "getTooltip", cancellable = true)
    private void modifyTooltip(PlayerEntity player, TooltipContext tooltipContext, CallbackInfoReturnable<List<Text>> cir) {
        if (!OriginAddons.onOriginRealms()) {
            cir.cancel();
            return;
        }

        ItemStack self = (ItemStack) (Object) this;
        List<Text> oldTooltip = cir.getReturnValue();
        List<Text> newTooltip = new ArrayList<>();
        List<Text> advanced = new ArrayList<>();

        int auctionTooltipStart = -1;
        int auctionTooltipEnd = -1;

        if (OriginAddons.getConfig().customTooltips) {
            for (int i = 0; i < oldTooltip.size(); i++) {
                String toString = oldTooltip.get(i).getString();

                if (toString.contains("Price: ")) {
                    auctionTooltipStart = i;
                } else if (toString.contains("for users auctions") || toString.contains("to collect item") || toString.contains("Auction Sold")) {
                    auctionTooltipEnd = i;
                } else if (toString.contains("When in") || toString.contains("When on")) {
                    auctionTooltipStart = i;
                    auctionTooltipEnd = oldTooltip.size() - 1;
                }
            }
        }

        boolean wasBlank = false;
        for (int i = 0; i < oldTooltip.size(); i++) {
            if (i >= auctionTooltipStart && i <= auctionTooltipEnd) continue;
            Text oldText = oldTooltip.get(i);
            TextColor color = oldText.getStyle().getColor();
            boolean isBlank = oldText.getString().trim().isEmpty();

            if (!(isBlank && wasBlank)) {
                boolean isAdvanced = false;
                if (color != null && color.getName().equals(Formatting.DARK_GRAY.getName())) isAdvanced = true;
                if (oldText.getString().trim().startsWith("Durability")) isAdvanced = true;
                if (isAdvanced) {
                    advanced.add(oldText);
                } else {
                    newTooltip.add(oldText);
                }
            }

            wasBlank = isBlank;
        }

        while (newTooltip.size() > 2 && newTooltip.get(newTooltip.size() - 1).getString().trim().isEmpty()) {
            newTooltip.remove(newTooltip.size() - 1);
        }

        ItemStackUtils.appendCustomTooltip(self, player, newTooltip);

        if (auctionTooltipStart >= 0 && auctionTooltipEnd >= 0) {
            if (newTooltip.size() > 1 && !newTooltip.get(newTooltip.size() - 1).getString().trim().isEmpty()) {
                newTooltip.add(Text.translatable(""));
            }

            for (int i = auctionTooltipStart; i <= auctionTooltipEnd; i++) {
                newTooltip.add(oldTooltip.get(i));
            }
        }

        newTooltip.addAll(advanced);

        cir.setReturnValue(newTooltip);
    }
}