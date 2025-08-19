package de.melanx.extradisks;

import com.refinedmods.refinedstorage.common.storage.StorageContainerUpgradeRecipe;
import com.refinedmods.refinedstorage.common.storage.StorageContainerUpgradeRecipeSerializer;
import de.melanx.extradisks.content.chemical.ExtraChemicalStorageVariant;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.content.item.ExtraItemStorageVariant;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExtraDisks {

    public static final String MODID = "extradisks";
    public static final Logger LOGGER = LoggerFactory.getLogger(ExtraDisks.class);

    public void onInitialize() {
        Registration.init();
        Registration.registerExtras();
        NeoForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.SERVER, de.melanx.extradisks.ModConfig.CONFIG);

        ExtraDisks.registerRecipeSerializers();
    }

    private static void registerRecipeSerializers() {
        Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                id("item_storage_disk_upgrade"),
                new StorageContainerUpgradeRecipeSerializer<>(
                        ExtraItemStorageVariant.values(),
                        to -> new StorageContainerUpgradeRecipe<>(
                                ExtraItemStorageVariant.values(), to, Registration.ITEM_STORAGE_DISK::get
                        )
                )
        );
        Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                id("item_storage_block_upgrade"),
                new StorageContainerUpgradeRecipeSerializer<>(
                        ExtraItemStorageVariant.values(),
                        to -> new StorageContainerUpgradeRecipe<>(
                                ExtraItemStorageVariant.values(), to, Registration.ITEM_STORAGE_BLOCK::get
                        )
                )
        );

        Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                id("fluid_storage_disk_upgrade"),
                new StorageContainerUpgradeRecipeSerializer<>(
                        ExtraFluidStorageVariant.values(),
                        to -> new StorageContainerUpgradeRecipe<>(
                                ExtraFluidStorageVariant.values(), to, Registration.FLUID_STORAGE_DISK::get
                        )
                )
        );
        Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                id("fluid_storage_block_upgrade"),
                new StorageContainerUpgradeRecipeSerializer<>(
                        ExtraFluidStorageVariant.values(),
                        to -> new StorageContainerUpgradeRecipe<>(
                                ExtraFluidStorageVariant.values(), to, Registration.FLUID_STORAGE_BLOCK::get
                        )
                )
        );

        Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                id("chemical_storage_disk_upgrade"),
                new StorageContainerUpgradeRecipeSerializer<>(
                        ExtraChemicalStorageVariant.values(),
                        to -> new StorageContainerUpgradeRecipe<>(
                                ExtraChemicalStorageVariant.values(), to, Registration.CHEMICAL_STORAGE_DISK::get
                        )
                )
        );
        Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                id("chemical_storage_block_upgrade"),
                new StorageContainerUpgradeRecipeSerializer<>(
                        ExtraChemicalStorageVariant.values(),
                        to -> new StorageContainerUpgradeRecipe<>(
                                ExtraChemicalStorageVariant.values(), to, Registration.CHEMICAL_STORAGE_BLOCK::get
                        )
                )
        );
    }

    private static ResourceLocation id(String fluidStorageBlockUpgrade) {
        return ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, fluidStorageBlockUpgrade);
    }
}
