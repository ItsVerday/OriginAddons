package com.mikarific.originaddons.util;

import com.mikarific.originaddons.OriginAddons;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemStackUtils {
    public static final Style STYLE_GRAY = Style.EMPTY.withColor(Formatting.GRAY);
    public static final Style STYLE_WHITE = Style.EMPTY.withColor(Formatting.WHITE);

    public static String getItemStackCustomID(ItemStack itemStack) {
        NbtCompound itemNBT = itemStack.getNbt();
        if (itemNBT == null) return "";
        if (itemNBT.contains("CustomBlock")) return itemNBT.getString("CustomBlock");
        if (itemNBT.contains("PublicBukkitValues")) {
            NbtCompound publicBukkitValues = itemNBT.getCompound("PublicBukkitValues");
            if (publicBukkitValues.contains("xcore:item-block"))
                return publicBukkitValues.getString("xcore:item-block");
            if (publicBukkitValues.contains("xcore:item-registry-key"))
                return publicBukkitValues.getString("xcore:item-registry-key");
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
                for (Text line : tooltip) {
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

                if (OriginAddons.getConfig().customTooltips && OriginAddons.getConfig().customBottledExperienceLevelsFrom0Tooltip)
                    tooltip.add(index, Text.translatable("originaddons.tooltips.bottled_experience.level_0").setStyle(STYLE_GRAY).append(Text.literal(levelFormat.format(calculateLeveling(player, expAmount))).setStyle(STYLE_WHITE)));
                if (OriginAddons.getConfig().customTooltips && OriginAddons.getConfig().customBottledExperienceLevelsFromCurrentTooltip)
                    tooltip.add(index, Text.translatable("originaddons.tooltips.bottled_experience.level_current").setStyle(STYLE_GRAY).append(Text.literal(levelFormat.format(calculateLeveling(player, playerExperience + expAmount))).setStyle(STYLE_WHITE)));

                return;
            }

            case "rocket_boots_30":
            case "rocket_boots_90": {
                if (OriginAddons.getConfig().customTooltips && !OriginAddons.getConfig().rocketBootsItemBarType.equals(RocketBootsItemBarType.DURABILITY))
                    tooltip.add(Text.translatable("originaddons.tooltips.rocket_boots.durability").setStyle(STYLE_GRAY).append(Text.literal((itemStack.getMaxDamage() - itemStack.getDamage()) + "/" + itemStack.getMaxDamage()).setStyle(STYLE_WHITE)));

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

    private static final ArrayList<String> CROP_CRATES = new ArrayList<>();

    static {
        CROP_CRATES.add("apple_basket");
        CROP_CRATES.add("banana_basket");
        CROP_CRATES.add("mango_basket");
        CROP_CRATES.add("pineapple_basket");
        CROP_CRATES.add("chilli_crate");
        CROP_CRATES.add("corn_crate");
        CROP_CRATES.add("eggplant_crate");
        CROP_CRATES.add("lettuce_crate");
    }

    public static Identifier getItemOverlayIdentifier(ItemStack itemStack) {
        NbtCompound itemNBT = itemStack.getNbt();
        if (itemNBT == null) itemNBT = new NbtCompound();

        boolean customCrop = false;
        int cropStars = 1;
        if (itemNBT.contains("CustomBlock")) {
            String customBlock = itemNBT.getString("CustomBlock");
            if (CROP_CRATES.contains(customBlock)) {
                customCrop = true;
            }
        }

        if (itemNBT.contains("PublicBukkitValues")) {
            NbtCompound publicBukkitValues = itemNBT.getCompound("PublicBukkitValues");

            if (publicBukkitValues.contains("xcore:item-identifier")) {
                String id = publicBukkitValues.getString("xcore:item-identifier");

                if (id.contains("Crop")) customCrop = true;
            }

            if (publicBukkitValues.contains("Stars")) {
                cropStars = publicBukkitValues.getInt("Stars");
                customCrop = true;
            }

            if (publicBukkitValues.contains("CrateStars")) {
                cropStars = publicBukkitValues.getInt("CrateStars");
                customCrop = true;
            }

            if (publicBukkitValues.contains("Golden")) {
                customCrop = false;
            }

            if (publicBukkitValues.contains("IsShopItem") && publicBukkitValues.getByte("IsShopItem") == 1) {
                customCrop = false;
            }
        }

        if (customCrop && OriginAddons.getConfig().cropStarsIcon)
            return new Identifier("originaddons", "textures/crop_overlays/" + cropStars + "_star.png");

        return null;
    }

    public static ItemBarInfo getCustomItemBar(ItemStack itemStack) {
        String customID = getItemStackCustomID(itemStack);
        if (customID.startsWith("rocket_boots_")) {
            if (OriginAddons.getConfig().rocketBootsItemBarType.equals(RocketBootsItemBarType.DURABILITY)) return null;

            float durabilityFraction = 1.0f - (float) itemStack.getDamage() / itemStack.getMaxDamage();
            float fraction = getRocketBootsFuelFraction(itemStack, customID);
            if (OriginAddons.getConfig().rocketBootsItemBarType.equals(RocketBootsItemBarType.LOWEST) && durabilityFraction < fraction) return null;

            int color;

            if (fraction > 0.75) {
                color = MathHelper.hsvToRgb(0.5F, 1.0F - (fraction - 0.75F) * 2.0F, 1.0F);
            } else if (fraction > 0.25) {
                color = MathHelper.hsvToRgb(0.666F - 0.166F * (fraction - 0.25F) * 2.0F, 1.0F, 1.0F);
            } else {
                color = MathHelper.hsvToRgb(0.666F, 1.0F, 1.0F - (0.25F - fraction) * 2.0F);
            }

            return new ItemBarInfo(color, fraction);
        }

        return null;
    }

    public static int getMaximumRocketBootsFuel(String id) {
        switch (id) {
            case "rocket_boots_90":
                return 90;
            case "rocket_boots_30":
                return 30;
            default:
                return 0;
        }
    }

    public static float getRocketBootsFuelFraction(ItemStack itemStack, String id) {
        int maxFuel = getMaximumRocketBootsFuel(id);
        int currentFuel = maxFuel;

        List<Text> tooltip = itemStack.getTooltip(MinecraftClient.getInstance().player, TooltipContext.Default.NORMAL);

        for (Text text : tooltip) {
            String toString = text.getString();

            if (toString.contains("Fuel: ")) {
                currentFuel = Integer.parseInt(toString.substring("Fuel: ".length()));
            }
        }

        return (float) currentFuel / maxFuel;
    }

    public static int getCustomItemBarStep(ItemStack itemStack) {
        String customID = getItemStackCustomID(itemStack);

        if (customID.startsWith("rocket_boots_")) {
            return Math.round(13.0F * getRocketBootsFuelFraction(itemStack, customID));
        }

        return 13;
    }
}
