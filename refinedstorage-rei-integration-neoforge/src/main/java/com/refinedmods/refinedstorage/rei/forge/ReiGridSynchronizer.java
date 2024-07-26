package com.refinedmods.refinedstorage.rei.forge;

import com.refinedmods.refinedstorage.common.grid.AbstractGridSynchronizer;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import static com.refinedmods.refinedstorage.rei.common.Common.SYNCHRONIZER_HELP;
import static com.refinedmods.refinedstorage.rei.common.Common.SYNCHRONIZER_TITLE;
import static com.refinedmods.refinedstorage.rei.common.Common.TWO_WAY_SYNCHRONIZER_HELP;
import static com.refinedmods.refinedstorage.rei.common.Common.TWO_WAY_SYNCHRONIZER_TITLE;

public class ReiGridSynchronizer extends AbstractGridSynchronizer {
    private final boolean twoWay;

    public ReiGridSynchronizer(final boolean twoWay) {
        this.twoWay = twoWay;
    }

    @Override
    public MutableComponent getTitle() {
        return twoWay ? TWO_WAY_SYNCHRONIZER_TITLE : SYNCHRONIZER_TITLE;
    }

    @Override
    public Component getHelpText() {
        return twoWay ? TWO_WAY_SYNCHRONIZER_HELP : SYNCHRONIZER_HELP;
    }

    @Override
    public void synchronizeFromGrid(final String text) {
        ReiHelper.setSearchFieldText(text);
    }

    @Override
    @Nullable
    public String getTextToSynchronizeToGrid() {
        return twoWay ? ReiHelper.getSearchFieldText() : null;
    }

    @Override
    public int getXTexture() {
        return twoWay ? 32 : 48;
    }
}
