package com.mikarific.originaddons.mixin.perf.item_model_caching;

import net.minecraft.client.render.model.BakedModel;

public interface ModelCachedItemStack {
    BakedModel match(float[] predicateValues);
    void use(BakedModel bakedModel, float[] predicateValues);
}
