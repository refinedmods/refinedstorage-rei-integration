package com.refinedmods.refinedstorage.rei.forge;

import com.refinedmods.refinedstorage.rei.common.Common;

import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

import static com.refinedmods.refinedstorage.rei.common.Common.SYNCHRONIZER_ID;
import static com.refinedmods.refinedstorage.rei.common.Common.TWO_WAY_SYNCHRONIZER_ID;

@Mod(Common.MOD_ID)
public class ModInitializer {
    public ModInitializer(final IEventBus eventBus) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            eventBus.addListener(ModInitializer::onClientSetup);
        }
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent e) {
        PlatformApi.INSTANCE.addIngredientConverter(new ReiRecipeModIngredientConverter());
        PlatformApi.INSTANCE.getGridSynchronizerRegistry().register(SYNCHRONIZER_ID, new ReiGridSynchronizer(false));
        PlatformApi.INSTANCE.getGridSynchronizerRegistry().register(
            TWO_WAY_SYNCHRONIZER_ID,
            new ReiGridSynchronizer(true)
        );
    }
}
