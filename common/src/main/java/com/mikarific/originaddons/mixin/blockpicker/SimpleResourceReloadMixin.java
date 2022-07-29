package com.mikarific.originaddons.mixin.blockpicker;

import com.google.common.collect.ImmutableList;
import com.mikarific.originaddons.OriginAddons;
import com.mikarific.originaddons.util.blockpicker.BlockPickResourceReloader;
import com.mikarific.originaddons.util.blockpicker.BlockPicker;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.resource.SimpleResourceReload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(SimpleResourceReload.class)
public class SimpleResourceReloadMixin {
    private static final BlockPickResourceReloader reloader = new BlockPickResourceReloader();

    @ModifyArg(method = "start", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/SimpleResourceReload;create(Lnet/minecraft/resource/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;)Lnet/minecraft/resource/SimpleResourceReload;"))
    private static List<ResourceReloader> injectResourceReloader(List<ResourceReloader> oldReloaders) {
        BlockPicker.clear();
        ImmutableList.Builder<ResourceReloader> builder = ImmutableList.builder();
        builder.addAll(oldReloaders);

        if (OriginAddons.onOriginRealms()) builder.add(reloader);
        return builder.build();
    }
}