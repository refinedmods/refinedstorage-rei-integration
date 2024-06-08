package com.refinedmods.refinedstorage.rei.fabric;

import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import net.fabricmc.api.ClientModInitializer;

import static com.refinedmods.refinedstorage.rei.common.Common.SYNCHRONIZER_ID;
import static com.refinedmods.refinedstorage.rei.common.Common.TWO_WAY_SYNCHRONIZER_ID;

public class ClientModInitializerImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PlatformApi.INSTANCE.addIngredientConverter(new ReiRecipeModIngredientConverter());
        PlatformApi.INSTANCE.getGridSynchronizerRegistry().register(SYNCHRONIZER_ID, new ReiGridSynchronizer(false));
        PlatformApi.INSTANCE.getGridSynchronizerRegistry().register(
            TWO_WAY_SYNCHRONIZER_ID,
            new ReiGridSynchronizer(true)
        );
    }
}
