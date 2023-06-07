package com.mikarific.originaddons.mixin.perf.item_model_caching;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ModelOverrideList.class)
public class ModelOverrideListMixin {
    @Inject(method = "apply", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/model/json/ModelOverrideList;overrides:[Lnet/minecraft/client/render/model/json/ModelOverrideList$BakedOverride;",
            ordinal = 1
    ), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void applyWithCache(BakedModel model, ItemStack stack, ClientWorld world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir, Item item, int i, float[] fs) {
        ModelCachedItemStack modelCachedItemStack = (ModelCachedItemStack) (Object) stack;
        BakedModel cachedModel = modelCachedItemStack.match(fs);
        if (cachedModel != null) {
            cir.setReturnValue(cachedModel);
        }
    }

    @Inject(method = "apply", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void setCachedItem(BakedModel model, ItemStack stack, ClientWorld world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir, Item item, int i, float[] fs, ModelOverrideList.BakedOverride[] var9, int var10, int var11, ModelOverrideList.BakedOverride bakedOverride, BakedModel bakedModel) {

    }
}
