package com.refinedmods.refinedstorage.rei.forge;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.autocrafting.PatternGridContainerMenu;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import me.shedaniel.rei.api.client.registry.transfer.TransferHandler;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import net.minecraft.world.item.ItemStack;

class PatternGridTransferHandler implements TransferHandler {
    @Override
    public Result handle(final Context context) {
        if (!(context.getMenu() instanceof PatternGridContainerMenu containerMenu)) {
            return Result.createNotApplicable();
        }
        final Result result = transferRegularRecipe(context, containerMenu);
        if (result != null) {
            return result;
        }
        return transferProcessingRecipe(context, containerMenu);
    }

    @Nullable
    private Result transferRegularRecipe(final Context context, final PatternGridContainerMenu containerMenu) {
        if (context.getDisplay().getCategoryIdentifier().equals(BuiltinPlugin.CRAFTING)
            && context.getDisplay() instanceof DefaultCraftingDisplay<?> defaultCraftingDisplay) {
            return transferCraftingRecipe(context, containerMenu, defaultCraftingDisplay);
        } else if (context.getDisplay().getCategoryIdentifier().equals(BuiltinPlugin.STONE_CUTTING)) {
            return transferStonecutterRecipe(context, containerMenu);
        } else if (context.getDisplay().getCategoryIdentifier().equals(BuiltinPlugin.SMITHING)) {
            return transferSmithingTableRecipe(context, containerMenu);
        }
        return null;
    }

    private Result transferCraftingRecipe(final Context context,
                                          final PatternGridContainerMenu containerMenu,
                                          final DefaultCraftingDisplay<?> display) {
        final List<List<ItemResource>> inputs = getItems(
            display.getOrganisedInputEntries(3, 3)
        );
        if (context.isActuallyCrafting()) {
            containerMenu.transferCraftingRecipe(inputs);
        }
        return Result.createSuccessful().blocksFurtherHandling();
    }

    private Result transferStonecutterRecipe(final Context context, final PatternGridContainerMenu menu) {
        final List<List<ItemResource>> inputs = getItems(context.getDisplay().getInputEntries());
        final List<List<ItemResource>> outputs = getItems(context.getDisplay().getOutputEntries());
        final boolean valid = !inputs.isEmpty()
            && !outputs.isEmpty()
            && !inputs.getFirst().isEmpty()
            && !outputs.getFirst().isEmpty();
        if (context.isActuallyCrafting() && valid) {
            menu.transferStonecutterRecipe(inputs.getFirst().getFirst(), outputs.getFirst().getFirst());
        }
        return Result.createSuccessful().blocksFurtherHandling();
    }

    private Result transferSmithingTableRecipe(final Context context, final PatternGridContainerMenu menu) {
        final List<List<ItemResource>> inputs = getItems(context.getDisplay().getInputEntries());
        if (context.isActuallyCrafting() && inputs.size() == 3) {
            menu.transferSmithingTableRecipe(inputs.get(0), inputs.get(1), inputs.get(2));
        }
        return Result.createSuccessful().blocksFurtherHandling();
    }

    private Result transferProcessingRecipe(final Context context, final PatternGridContainerMenu menu) {
        final List<List<ResourceAmount>> inputs = getResources(context.getDisplay().getInputEntries());
        final List<List<ResourceAmount>> outputs = getResources(context.getDisplay().getOutputEntries());
        if (context.isActuallyCrafting()) {
            menu.transferProcessingRecipe(inputs, outputs);
        }
        return Result.createSuccessful().blocksFurtherHandling();
    }

    private List<List<ItemResource>> getItems(final List<EntryIngredient> ingredients) {
        return ingredients.stream()
            .map(this::convertIngredientToItemStacks)
            .map(list -> list.stream().map(ItemResource::ofItemStack).collect(Collectors.toList()))
            .toList();
    }

    private List<ItemStack> convertIngredientToItemStacks(final EntryIngredient ingredient) {
        return CollectionUtils.<EntryStack<?>, ItemStack>filterAndMap(
            ingredient,
            stack -> stack.getType() == VanillaEntryTypes.ITEM,
            EntryStack::castValue
        );
    }

    private List<List<ResourceAmount>> getResources(final List<EntryIngredient> ingredients) {
        return ingredients.stream()
            .map(ingredient -> ingredient.stream()
                .flatMap(
                    item -> RefinedStorageApi.INSTANCE.getIngredientConverter().convertToResourceAmount(item.getValue())
                        .stream())
                .collect(Collectors.toList()))
            .toList();
    }
}
