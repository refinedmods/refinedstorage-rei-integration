package com.refinedmods.refinedstorage.rei.fabric;

import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import com.refinedmods.refinedstorage2.platform.api.RefinedStoragePlugin;

import static com.refinedmods.refinedstorage.rei.common.Common.SYNCHRONIZER_ID;
import static com.refinedmods.refinedstorage.rei.common.Common.TWO_WAY_SYNCHRONIZER_ID;

public class ReiRefinedStoragePlugin implements RefinedStoragePlugin {
    @Override
    public void onPlatformApiAvailable(final PlatformApi platformApi) {
        platformApi.addIngredientConverter(new ReiRecipeModIngredientConverter());
        platformApi.getGridSynchronizerRegistry().register(SYNCHRONIZER_ID, new ReiGridSynchronizer(false));
        platformApi.getGridSynchronizerRegistry().register(TWO_WAY_SYNCHRONIZER_ID, new ReiGridSynchronizer(true));
    }
}
