package com.refinedmods.refinedstorage.rei.fabric;

import java.util.function.Supplier;

import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import com.refinedmods.refinedstorage2.platform.api.support.resource.RecipeModIngredientConverter;
import com.refinedmods.refinedstorage2.platform.common.content.BlockColorMap;
import com.refinedmods.refinedstorage2.platform.common.content.Blocks;
import com.refinedmods.refinedstorage2.platform.common.content.ContentIds;
import com.refinedmods.refinedstorage2.platform.common.content.Items;
import com.refinedmods.refinedstorage2.platform.common.content.Tags;
import com.refinedmods.refinedstorage2.platform.common.controller.ControllerBlockItem;
import com.refinedmods.refinedstorage2.platform.common.support.AbstractBaseScreen;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.entry.CollapsibleEntryRegistry;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.refinedmods.refinedstorage.rei.common.Common.FULLY_CHARGED_CONTROLLER_GROUP_ID;
import static com.refinedmods.refinedstorage.rei.common.Common.FULLY_CHARGED_CONTROLLER_GROUP_NAME;

@Environment(EnvType.CLIENT)
public class ReiClientPlugin implements REIClientPlugin {
    @Override
    public void registerScreens(final ScreenRegistry registry) {
        registry.registerFocusedStack(new GridFocusedStackProvider());
        registry.registerFocusedStack(new ResourceFocusedStackProvider());
        registry.registerDraggableStackVisitor(new DraggableStackVisitorImpl());
    }

    @Override
    public void registerTransferHandlers(final TransferHandlerRegistry registry) {
        registry.register(new CraftingGridTransferHandler());
    }

    @Override
    public void registerItemComparators(final ItemComparatorRegistry registry) {
        Items.INSTANCE.getControllers().stream().map(Supplier::get).forEach(registry::registerNbt);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void registerCollapsibleEntries(final CollapsibleEntryRegistry registry) {
        groupItems(registry, Blocks.INSTANCE.getCable(), ContentIds.CABLE, Tags.CABLES);
        groupItems(registry, Blocks.INSTANCE.getGrid(), ContentIds.GRID, Tags.GRIDS);
        groupItems(registry, Blocks.INSTANCE.getCraftingGrid(), ContentIds.CRAFTING_GRID, Tags.CRAFTING_GRIDS);
        groupItems(registry, Blocks.INSTANCE.getImporter(), ContentIds.IMPORTER, Tags.IMPORTERS);
        groupItems(registry, Blocks.INSTANCE.getExporter(), ContentIds.EXPORTER, Tags.EXPORTERS);
        groupItems(registry, Blocks.INSTANCE.getDetector(), ContentIds.DETECTOR, Tags.DETECTORS);
        groupItems(registry, Blocks.INSTANCE.getDestructor(), ContentIds.DESTRUCTOR, Tags.DESTRUCTORS);
        groupItems(registry, Blocks.INSTANCE.getConstructor(), ContentIds.CONSTRUCTOR, Tags.CONSTRUCTORS);
        groupItems(registry, Blocks.INSTANCE.getExternalStorage(), ContentIds.EXTERNAL_STORAGE, Tags.EXTERNAL_STORAGES);
        groupItems(registry, Blocks.INSTANCE.getController(), ContentIds.CONTROLLER, Tags.CONTROLLERS);
        registry.group(
            FULLY_CHARGED_CONTROLLER_GROUP_ID,
            FULLY_CHARGED_CONTROLLER_GROUP_NAME,
            Items.INSTANCE.getControllers()
                .stream()
                .map(Supplier::get)
                .map(ControllerBlockItem::createAtEnergyCapacity)
                .map(EntryStacks::of)
                .toList()
        );
        groupItems(
            registry,
            Blocks.INSTANCE.getCreativeController(),
            ContentIds.CREATIVE_CONTROLLER,
            Tags.CREATIVE_CONTROLLERS
        );
        groupItems(
            registry,
            Blocks.INSTANCE.getWirelessTransmitter(),
            ContentIds.WIRELESS_TRANSMITTER,
            Tags.WIRELESS_TRANSMITTERS
        );
        groupItems(registry, Blocks.INSTANCE.getNetworkReceiver(), ContentIds.NETWORK_RECEIVER, Tags.NETWORK_RECEIVERS);
        groupItems(
            registry,
            Blocks.INSTANCE.getNetworkTransmitter(),
            ContentIds.NETWORK_TRANSMITTER,
            Tags.NETWORK_TRANSMITTERS
        );
        groupItems(
            registry,
            Blocks.INSTANCE.getSecurityManager(),
            ContentIds.SECURITY_MANAGER,
            Tags.SECURITY_MANAGERS
        );
        groupItems(
            registry,
            Blocks.INSTANCE.getRelay(),
            ContentIds.RELAY,
            Tags.RELAYS
        );
    }

    @Override
    public void registerExclusionZones(final ExclusionZones zones) {
        zones.register(AbstractBaseScreen.class, new ExclusionZonesProviderImpl());
    }

    @SuppressWarnings("UnstableApiUsage")
    private static void groupItems(
        final CollapsibleEntryRegistry registry,
        final BlockColorMap<?, ?> blocks,
        final ResourceLocation itemIdentifier,
        final TagKey<Item> tag
    ) {
        registry.group(
            itemIdentifier,
            blocks.getDefault().getName(),
            EntryIngredients.ofItemTag(tag)
        );
    }
}
