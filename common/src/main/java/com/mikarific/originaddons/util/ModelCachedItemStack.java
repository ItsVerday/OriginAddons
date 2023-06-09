package com.mikarific.originaddons.util;

import net.minecraft.client.render.model.BakedModel;

public interface ModelCachedItemStack {
    BakedModel match(float[] predicateValues);
    void use(BakedModel bakedModel, float[] predicateValues);

    default void clearCachedModel() {
        use(null, null);
    }
}
