package com.refinedmods.refinedstorage.rei.forge;

import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import javax.annotation.Nullable;

import me.shedaniel.rei.api.common.entry.EntryIngredient;

record TransferInput(EntryIngredient ingredient, TransferInputType type, @Nullable ItemResource autocraftableResource) {
}
