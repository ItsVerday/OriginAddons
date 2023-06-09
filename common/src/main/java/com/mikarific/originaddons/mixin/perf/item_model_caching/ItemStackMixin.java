package com.mikarific.originaddons.mixin.perf.item_model_caching;

import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.ModelCachedItemStack;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin implements ModelCachedItemStack {
    private float[] cachedPredicateValues = null;
    private BakedModel cachedModel = null;

    @Override
    public BakedModel match(float[] predicateValues) {
        if (cachedPredicateValues == null) return null;
        if (cachedModel == null) return null;
        if (predicateValues.length != cachedPredicateValues.length) return null;
        for (int i = 0; i < predicateValues.length; i++) {
            if (predicateValues[i] != cachedPredicateValues[i]) return null;
        }

        return cachedModel;
    }

    @Override
    public void use(BakedModel bakedModel, float[] predicateValues) {
        this.cachedModel = bakedModel;
        this.cachedPredicateValues = predicateValues;
    }

    @Inject(method = "setCount", at = @At("RETURN"))
    private void setCountClearCache(int count, CallbackInfo ci) {
        clearCachedModel();
    }

    @Inject(method = "setDamage", at = @At("RETURN"))
    private void setDamageClearCache(int damage, CallbackInfo ci) {
        clearCachedModel();
    }

    @Inject(method = "setNbt", at = @At("RETURN"))
    private void setNbtClearCache(NbtCompound nbt, CallbackInfo ci) {
        clearCachedModel();
    }

    @Inject(method = "setSubNbt", at = @At("RETURN"))
    private void setSubNbtClearCache(String key, NbtElement element, CallbackInfo ci) {
        clearCachedModel();
    }
}
