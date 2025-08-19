package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.content.item.ExtraItemStorageVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ExtraAdvancementProvider extends FabricAdvancementProvider {

    protected ExtraAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        Advancement.Builder.advancement().display(Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageVariant.TIER_12), Component.translatable("advancements.extradisks.infinite_storage.title"), Component.translatable("advancements.extradisks.infinite_storage.description"), ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "textures/gui/advancements.png"), AdvancementType.CHALLENGE, true, true, true)
                .addCriterion("has_storage", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(
                                Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageVariant.TIER_12),
                                Registration.FLUID_STORAGE_DISK.get(ExtraFluidStorageVariant.TIER_9_FLUID),
                                Registration.ITEM_STORAGE_BLOCK.get(ExtraItemStorageVariant.TIER_12),
                                Registration.FLUID_STORAGE_BLOCK.get(ExtraFluidStorageVariant.TIER_9_FLUID)
                        ).build()
                )).save(consumer, ExtraDisks.MODID + ":infinite_storage");
    }
}
