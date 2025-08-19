package de.melanx.extradisks.data;

import com.refinedmods.refinedstorage.common.content.Blocks;
import com.refinedmods.refinedstorage.common.content.Items;
import com.refinedmods.refinedstorage.common.content.Tags;
import com.refinedmods.refinedstorage.common.misc.ProcessorItem;
import com.refinedmods.refinedstorage.common.storage.FluidStorageVariant;
import com.refinedmods.refinedstorage.common.storage.ItemStorageVariant;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import de.melanx.extradisks.content.chemical.ExtraChemicalStorageDiskItem;
import de.melanx.extradisks.content.chemical.ExtraChemicalStorageVariant;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.content.item.ExtraItemStorageDiskItem;
import de.melanx.extradisks.content.item.ExtraItemStorageVariant;
import de.melanx.extradisks.recipes.builder.StorageContainerUpgradeRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Recipes extends FabricRecipeProvider {

    public Recipes(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(@Nonnull RecipeOutput recipeOutput) {

        for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
            this.registerDiskRecipes(Registration.ITEM_STORAGE_DISK.get(variant), ModTags.Items.PARTS_ITEM.get(variant), recipeOutput);
            this.registerStorageBlockRecipe(ModTags.Items.PARTS_ITEM.get(variant), Registration.ITEM_STORAGE_BLOCK.get(variant), recipeOutput);
        }

        for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
            this.registerDiskRecipes(Registration.FLUID_STORAGE_DISK.get(variant), ModTags.Items.PARTS_FLUID.get(variant), recipeOutput);
            this.registerStorageBlockRecipe(ModTags.Items.PARTS_FLUID.get(variant), Registration.FLUID_STORAGE_BLOCK.get(variant), recipeOutput);
        }

        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_5), Items.INSTANCE.getItemStoragePart(ItemStorageVariant.SIXTY_FOUR_K), recipeOutput);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_6), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_5), recipeOutput);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_7), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_6), recipeOutput);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_8), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_7), recipeOutput);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_9), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_8), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_10), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_9), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_11), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_10), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_12), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_11), recipeOutput);

        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_5_FLUID), Items.INSTANCE.getFluidStoragePart(FluidStorageVariant.FOUR_THOUSAND_NINETY_SIX_B), recipeOutput);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_6_FLUID), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_5_FLUID), recipeOutput);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_7_FLUID), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_6_FLUID), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_8_FLUID), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_7_FLUID), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_9_FLUID), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_8_FLUID), recipeOutput);

       // this.registerMekanismPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_5_CHEMICAL), com.refinedmods.refinedstorage.mekanism.content.Items.getChemicalStoragePart(ChemicalStorageVariant.EIGHT_THOUSAND_NINETY_TWO_B), mekanismRecipeOutput);
      //  this.registerMekanismPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_6_CHEMICAL), ModTags.Items.PARTS_CHEMICAL.get(ExtraChemicalStorageVariant.TIER_5_CHEMICAL), mekanismRecipeOutput);
     //   this.registerMekanismPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_7_CHEMICAL), ModTags.Items.PARTS_CHEMICAL.get(ExtraChemicalStorageVariant.TIER_6_CHEMICAL), mekanismRecipeOutput);
     //   this.registerMekanismAdvancedPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_8_CHEMICAL), ModTags.Items.PARTS_CHEMICAL.get(ExtraChemicalStorageVariant.TIER_7_CHEMICAL), mekanismRecipeOutput);
      //  this.registerMekanismAdvancedPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_9_CHEMICAL), ModTags.Items.PARTS_CHEMICAL.get(ExtraChemicalStorageVariant.TIER_8_CHEMICAL), mekanismRecipeOutput);

        this.registerProcessorRecipe(Registration.WITHERING_PROCESSOR, Registration.RAW_WITHERING_PROCESSOR, Ingredient.of(net.minecraft.world.item.Items.NETHER_STAR), recipeOutput);

        this.registerUpgrades(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ADVANCED_STORAGE_HOUSING)
                .pattern("GEG")
                .pattern("E E")
                .pattern("IAI")
                .define('G', ConventionalItemTags.GLASS_BLOCKS)
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('I', Items.INSTANCE.getProcessor(ProcessorItem.Type.IMPROVED))
                .define('A', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .unlockedBy("has_processor", has(Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED)))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ADVANCED_MACHINE_CASING)
                .pattern("DCD")
                .pattern("GBG")
                .pattern("DOD")
                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .define('C', Items.INSTANCE.getConstructionCore())
                .define('G', Items.INSTANCE.getProcessor(ProcessorItem.Type.IMPROVED))
                .define('B', Blocks.INSTANCE.getMachineCasing())
                .define('O', Items.INSTANCE.getDestructionCore())
                .unlockedBy("has_processor", has(Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED)))
                .save(recipeOutput);
    }

    private void registerProcessorRecipe(ItemLike result, ItemLike raw, Ingredient ingredient, RecipeOutput recipeOutput) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(raw), RecipeCategory.MISC, result, 0.5F, 200)
                .unlockedBy("has_raw", has(raw))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, raw)
                .requires(Items.INSTANCE.getProcessorBinding())
                .requires(ingredient)
                .requires(Items.INSTANCE.getSilicon())
                .requires(ConventionalItemTags.REDSTONE_DUSTS)
                .unlockedBy("has_binding", has(Items.INSTANCE.getProcessorBinding()))
                .save(recipeOutput);
    }

    private void registerPartRecipe(Item result, Item prevPart, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', prevPart)
                .define('R', ConventionalItemTags.REDSTONE_DUSTS)
                .unlockedBy("has_prev_part", has(prevPart))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "part/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
    }

    private void registerPartRecipe(Item result, TagKey<Item> prevPart, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', prevPart)
                .define('R', ConventionalItemTags.REDSTONE_DUSTS)
                .unlockedBy("has_prev_part", has(prevPart))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "part/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
    }

    private void registerAdvancedPartRecipe(Item result, TagKey<Item> prevPart, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Registration.WITHERING_PROCESSOR)
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', prevPart)
                .define('R', ConventionalItemTags.REDSTONE_DUSTS)
                .unlockedBy("has_prev_part", has(prevPart))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "part/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
    }

    private void registerDiskRecipes(Item result, TagKey<Item> part, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("GEG")
                .pattern("EPE")
                .pattern("IAI")
                .define('G', ConventionalItemTags.GLASS_BLOCKS)
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', part)
                .define('I', Items.INSTANCE.getProcessor(ProcessorItem.Type.IMPROVED))
                .define('A', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .unlockedBy("has_part", has(part))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "disk/shaped/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                .requires(Registration.ADVANCED_STORAGE_HOUSING)
                .requires(part)
                .unlockedBy("has_part", has(part))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "disk/shapeless/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
    }

    private void registerStorageBlockRecipe(TagKey<Item> part, ItemLike block, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
                .pattern("QPQ")
                .pattern("QCQ")
                .pattern("QRQ")
                .define('Q', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', part)
                .define('C', Registration.ADVANCED_MACHINE_CASING)
                .define('R', ConventionalItemTags.REDSTONE_DUSTS)
                .unlockedBy("has_part", has(part))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "blocks/" + BuiltInRegistries.ITEM.getKey(block.asItem()).getPath()));
    }



    private void registerUpgrades(RecipeOutput recipeOutput) {
        this.registerItemStorageUpgrades(recipeOutput);
        this.registerFluidStorageUpgrades(recipeOutput);
    }

    private void registerItemStorageUpgrades(RecipeOutput recipeOutput) {
        Set<Ingredient.Value> disks = new HashSet<>();
        for (ItemStorageVariant value : ItemStorageVariant.values()) {
            if (value == ItemStorageVariant.CREATIVE) {
                continue;
            }

            disks.add(new Ingredient.ItemValue(Items.INSTANCE.getItemStorageDisk(value).getDefaultInstance()));
        }

        for (ExtraItemStorageVariant value : ExtraItemStorageVariant.values()) {
            ExtraItemStorageDiskItem disk = Registration.ITEM_STORAGE_DISK.get(value);
            StorageContainerUpgradeRecipeBuilder.shapeless(disk)
                    .disk(Ingredient.fromValues(disks.stream()))
                    .part(Ingredient.of(Registration.ITEM_STORAGE_PART.get(value)))
                    .save(recipeOutput);

            disks.add(new Ingredient.TagValue(ModTags.Items.DISKS_ITEM.get(value)));
        }

        Set<ItemLike> storageBlocks = new HashSet<>();
        for (ItemStorageVariant value : ItemStorageVariant.values()) {
            if (value == ItemStorageVariant.CREATIVE) {
                continue;
            }

            storageBlocks.add(Blocks.INSTANCE.getItemStorageBlock(value));
        }

        for (ExtraItemStorageVariant value : ExtraItemStorageVariant.values()) {
            Block storageBlock = Registration.ITEM_STORAGE_BLOCK.get(value);
            StorageContainerUpgradeRecipeBuilder.shapeless(storageBlock)
                    .disk(Ingredient.of(storageBlocks.toArray(new ItemLike[0])))
                    .part(Ingredient.of(Registration.ITEM_STORAGE_PART.get(value)))
                    .save(recipeOutput);

            storageBlocks.add(storageBlock);
        }
    }

    private void registerFluidStorageUpgrades(RecipeOutput recipeOutput) {
        Set<ItemLike> disks = new HashSet<>();
        for (FluidStorageVariant value : FluidStorageVariant.values()) {
            if (value == FluidStorageVariant.CREATIVE) {
                continue;
            }

            disks.add(Items.INSTANCE.getFluidStorageDisk(value));
        }

        for (ExtraFluidStorageVariant value : ExtraFluidStorageVariant.values()) {
            Item disk = Registration.FLUID_STORAGE_DISK.get(value);
            StorageContainerUpgradeRecipeBuilder.shapeless(disk)
                    .disk(Ingredient.of(disks.toArray(new ItemLike[0])))
                    .part(Ingredient.of(Registration.FLUID_STORAGE_PART.get(value)))
                    .save(recipeOutput);

            disks.add(disk);
        }

        Set<ItemLike> storageBlocks = new HashSet<>();
        for (FluidStorageVariant value : FluidStorageVariant.values()) {
            if (value == FluidStorageVariant.CREATIVE) {
                continue;
            }

            storageBlocks.add(Blocks.INSTANCE.getFluidStorageBlock(value));
        }

        for (ExtraFluidStorageVariant value : ExtraFluidStorageVariant.values()) {
            Block storageBlock = Registration.FLUID_STORAGE_BLOCK.get(value);
            StorageContainerUpgradeRecipeBuilder.shapeless(storageBlock)
                    .disk(Ingredient.of(storageBlocks.toArray(new ItemLike[0])))
                    .part(Ingredient.of(Registration.FLUID_STORAGE_PART.get(value)))
                    .save(recipeOutput);

            storageBlocks.add(storageBlock);
        }
    }

}