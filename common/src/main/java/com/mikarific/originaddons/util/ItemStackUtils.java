package com.mikarific.originaddons.util;

import com.mikarific.originaddons.OriginAddons;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemStackUtils {
    public static String getItemStackCustomID(ItemStack itemStack) {
        NbtCompound itemNBT = itemStack.getNbt();
        if (itemNBT == null) return "";
        if (itemNBT.contains("CustomBlock")) return itemNBT.getString("CustomBlock");
        if (itemNBT.contains("PublicBukkitValues")) {
            NbtCompound publicBukkitValues = itemNBT.getCompound("PublicBukkitValues");
            if (publicBukkitValues.contains("xcore:item-block")) return publicBukkitValues.getString("xcore:item-block");
            if (publicBukkitValues.contains("xcore:item-registry-key")) return publicBukkitValues.getString("xcore:item-registry-key");
        }

        return "";
    }

    public static final Pattern expAmountPattern = Pattern.compile("^Experience: ([0-9,]+)$");
    public static final DecimalFormat levelFormat = new DecimalFormat("#,###.00");

    public static void appendCustomTooltip(ItemStack itemStack, PlayerEntity player, List<Text> tooltip) {
        String customID = getItemStackCustomID(itemStack);
        if (customID.length() == 0) return;

        switch (customID) {
            case "bottled_experience": {
                int expAmount = 0;
                int index = -1;
                int currentIndex = -1;
                for (Text line: tooltip) {
                    currentIndex++;
                    Matcher matcher = expAmountPattern.matcher(line.getString().trim());
                    if (!matcher.find()) continue;

                    String matchedExpAmount = matcher.group(1).replaceAll(",", "");
                    try {
                        expAmount = Integer.parseInt(matchedExpAmount);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    index = currentIndex + 1;
                    break;
                }

                if (index == -1) return;

                long playerExperience = Math.round(getLevelExperience(player.experienceLevel) * player.experienceProgress);
                for (int i = 0; i < player.experienceLevel; i++) {
                    playerExperience += getLevelExperience(i);
                }

                Style gray = Style.EMPTY.withColor(Formatting.GRAY);
                Style white = Style.EMPTY.withColor(Formatting.WHITE);
                if (OriginAddons.getConfig().customBottledExperienceLevelsFrom0Tooltip) tooltip.add(index, new TranslatableText("originaddons.tooltips.bottled_experience.level_0").setStyle(gray).append(new LiteralText(levelFormat.format(calculateLeveling(player, playerExperience + expAmount))).setStyle(white)));
                if (OriginAddons.getConfig().customBottledExperienceLevelsFromCurrentTooltip) tooltip.add(index, new TranslatableText("originaddons.tooltips.bottled_experience.level_current").setStyle(gray).append(new LiteralText(levelFormat.format(calculateLeveling(player, expAmount))).setStyle(white)));

                return;
            }
        }
    }

    // Formula is from PlayerEntity class
    private static long getLevelExperience(long level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        } else {
            return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
        }
    }

    private static double calculateLeveling(PlayerEntity player, long startingExperience) {
        long remainingExperience = startingExperience;
        int level = 0;

        while (remainingExperience >= getLevelExperience(level)) {
            remainingExperience -= getLevelExperience(level);
            level++;
        }

        return level + (double) remainingExperience / (double) getLevelExperience(level);
    }
}
