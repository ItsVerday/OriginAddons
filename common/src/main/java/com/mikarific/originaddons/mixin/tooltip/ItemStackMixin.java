package com.mikarific.originaddons.mixin.tooltip;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.ItemStackUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
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

        int auctionTooltipStart = -1;
        int auctionTooltipEnd = -1;

        if (OriginAddons.getConfig().customTooltips) {
            for (int i = 0; i < oldTooltip.size(); i++) {
                String toString = oldTooltip.get(i).getString();

                if (toString.contains("Price: ")) {
                    auctionTooltipStart = i;
                } else if (toString.contains("Shift click for users auctions") || toString.contains("Shift click to collect item") || toString.contains("Auction Sold")) {
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
            boolean isBlank = oldText.getString().trim().length() == 0;

            if (!(isBlank && wasBlank)) {
                newTooltip.add(oldText);
            }

            wasBlank = isBlank;
        }

        while (newTooltip.size() > 2 && newTooltip.get(newTooltip.size() - 1).getString().trim().length() == 0) {
            newTooltip.remove(newTooltip.size() - 1);
        }

        ItemStackUtils.appendCustomTooltip(self, player, newTooltip);

        if (auctionTooltipStart >= 0 && auctionTooltipEnd >= 0) {
            if (newTooltip.size() > 1 && newTooltip.get(newTooltip.size() - 1).getString().trim().length() > 0) {
                newTooltip.add(new LiteralText(""));
            }

            for (int i = auctionTooltipStart; i <= auctionTooltipEnd; i++) {
                newTooltip.add(oldTooltip.get(i));
            }
        }

        cir.setReturnValue(newTooltip);
    }
}