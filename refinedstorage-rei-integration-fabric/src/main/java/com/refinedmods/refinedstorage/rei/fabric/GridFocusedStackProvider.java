package com.refinedmods.refinedstorage.rei.fabric;

import com.refinedmods.refinedstorage.platform.api.PlatformApi;
import com.refinedmods.refinedstorage.platform.api.grid.view.PlatformGridResource;
import com.refinedmods.refinedstorage.platform.api.support.resource.PlatformResourceKey;
import com.refinedmods.refinedstorage.platform.common.grid.screen.AbstractGridScreen;

import dev.architectury.event.CompoundEventResult;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.registry.screen.FocusedStackProvider;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.client.gui.screens.Screen;

class GridFocusedStackProvider implements FocusedStackProvider {
    @Override
    public CompoundEventResult<EntryStack<?>> provide(final Screen screen, final Point mouse) {
        if (!(screen instanceof AbstractGridScreen<?> gridScreen)) {
            return CompoundEventResult.pass();
        }
        final PlatformGridResource resource = gridScreen.getCurrentGridResource();
        if (resource == null) {
            return CompoundEventResult.pass();
        }
        final PlatformResourceKey underlyingResource = resource.getUnderlyingResource();
        if (underlyingResource == null) {
            return CompoundEventResult.pass();
        }
        return provide(underlyingResource);
    }

    private CompoundEventResult<EntryStack<?>> provide(final PlatformResourceKey resource) {
        final Object converted = PlatformApi.INSTANCE.getIngredientConverter()
            .convertToIngredient(resource)
            .orElse(null);
        if (converted instanceof EntryStack<?> stack) {
            return CompoundEventResult.interruptTrue(stack);
        }
        return CompoundEventResult.pass();
    }
}
