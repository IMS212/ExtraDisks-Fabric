package de.melanx.extradisks;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.support.network.AbstractNetworkNodeContainerBlockEntity;
import com.refinedmods.refinedstorage.common.content.*;
import com.refinedmods.refinedstorage.common.storage.storageblock.StorageBlock;
import de.melanx.extradisks.content.chemical.ExtraChemicalStorageDiskItem;
import de.melanx.extradisks.content.chemical.ExtraChemicalStorageVariant;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageBlockItem;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageBlockProvider;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.content.item.ExtraItemStorageBlockItem;
import de.melanx.extradisks.content.item.ExtraItemStorageBlockProvider;
import de.melanx.extradisks.content.item.ExtraItemStorageDiskItem;
import de.melanx.extradisks.content.item.ExtraItemStorageVariant;
import de.melanx.extradisks.loottable.ExtraLootFunctions;
import de.melanx.extradisks.recipes.StorageContainerUpgradeRecipe;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

public class Registration {

    private static final Item.Properties ITEM_PROPS = new Item.Properties();

    // item storage blocks
    public static final Map<ExtraItemStorageVariant, Block> ITEM_STORAGE_BLOCK = new HashMap<>();
    public static final Map<ExtraItemStorageVariant, Item> ITEM_STORAGE = new HashMap<>();
    public static final Map<ExtraItemStorageVariant, BlockEntityType<AbstractNetworkNodeContainerBlockEntity<?>>> ITEM_STORAGE_TILE = new HashMap<>();
    public static final Map<ExtraItemStorageVariant, MenuType<AbstractContainerMenu>> ITEM_STORAGE_CONTAINER = new HashMap<>();

    // fluid storage blocks
    public static final Map<ExtraFluidStorageVariant, Block> FLUID_STORAGE_BLOCK = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, Item> FLUID_STORAGE = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, BlockEntityType<AbstractNetworkNodeContainerBlockEntity<?>>> FLUID_STORAGE_TILE = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, MenuType<AbstractContainerMenu>> FLUID_STORAGE_CONTAINER = new HashMap<>();

    // chemical storage blocks
    public static final Map<ExtraChemicalStorageVariant, Block> CHEMICAL_STORAGE_BLOCK = new HashMap<>();
    public static final Map<ExtraChemicalStorageVariant, Item> CHEMICAL_STORAGE = new HashMap<>();
    public static final Map<ExtraChemicalStorageVariant, BlockEntityType<AbstractNetworkNodeContainerBlockEntity<?>>> CHEMICAL_STORAGE_TILE = new HashMap<>();
    public static final Map<ExtraChemicalStorageVariant, MenuType<AbstractContainerMenu>> CHEMICAL_STORAGE_CONTAINER = new HashMap<>();

    // item storage disks/parts
    public static final Map<ExtraItemStorageVariant, Item> ITEM_STORAGE_PART = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, Item> FLUID_STORAGE_PART = new HashMap<>();
    public static final Map<ExtraChemicalStorageVariant, Item> CHEMICAL_STORAGE_PART = new HashMap<>();
    public static final Map<ExtraItemStorageVariant, ExtraItemStorageDiskItem> ITEM_STORAGE_DISK = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, ExtraFluidStorageDiskItem> FLUID_STORAGE_DISK = new HashMap<>();
    public static final Map<ExtraChemicalStorageVariant, ExtraChemicalStorageDiskItem> CHEMICAL_STORAGE_DISK = new HashMap<>();

    public static final Block ADVANCED_MACHINE_CASING_BLOCK = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "advanced_machine_casing"), new Block(BlockConstants.PROPERTIES));
    public static final BlockItem ADVANCED_MACHINE_CASING = Registry.register(BuiltInRegistries.ITEM, id("advanced_machine_casing"), new BlockItem(ADVANCED_MACHINE_CASING_BLOCK, ITEM_PROPS));

    private static ResourceLocation id(String x) {
        return ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, x);
    }

    public static final Item ADVANCED_STORAGE_HOUSING = Registry.register(BuiltInRegistries.ITEM, id("advanced_storage_housing"), new Item(ITEM_PROPS));
    public static final Item RAW_WITHERING_PROCESSOR = Registry.register(BuiltInRegistries.ITEM, id("raw_withering_processor"), new Item(ITEM_PROPS));
    public static final Item WITHERING_PROCESSOR = Registry.register(BuiltInRegistries.ITEM, id("withering_processor"), new Item(ITEM_PROPS));

    public static final RecipeSerializer<StorageContainerUpgradeRecipe> UPGRADE_RECIPE = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id("upgrade_recipe"), new StorageContainerUpgradeRecipe.Serializer());
    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), id("extra_disks"));

    public static CreativeModeTab CUSTOM_ITEM_GROUP;

    public static void registerExtras() {
        CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
                .title(Component.literal("Extra Disks"))
                .icon(() -> new ItemStack(Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageVariant.TIER_8)))
                .build();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(output -> {
            output.accept(ADVANCED_MACHINE_CASING);
            output.accept(ADVANCED_STORAGE_HOUSING);
            output.accept(RAW_WITHERING_PROCESSOR);
            output.accept(WITHERING_PROCESSOR);

            // item storage
            for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                ExtraItemStorageDiskItem item = ITEM_STORAGE_DISK.get(variant);
                output.accept(item);
            }
            for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                Item item = ITEM_STORAGE.get(variant);
                output.accept(item);
            }
            for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                Item item = ITEM_STORAGE_PART.get(variant);
                output.accept(item);
            }

            // fluid storage
            for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                ExtraFluidStorageDiskItem item = FLUID_STORAGE_DISK.get(variant);
                output.accept(item);
            }
            for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                Item item = FLUID_STORAGE.get(variant);
                output.accept(item);
            }
            for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                Item item = FLUID_STORAGE_PART.get(variant);
                output.accept(item);
            }

            if (FabricLoader.getInstance().isModLoaded("mekanism") && FabricLoader.getInstance().isModLoaded("refinedstorage_mekanism_integration")) {
                // chemical storage
                for (ExtraChemicalStorageVariant variant : ExtraChemicalStorageVariant.values()) {
                    ExtraChemicalStorageDiskItem item = CHEMICAL_STORAGE_DISK.get(variant);
                    output.accept(item);
                }
                for (ExtraChemicalStorageVariant variant : ExtraChemicalStorageVariant.values()) {
                    Item item = CHEMICAL_STORAGE.get(variant);
                    output.accept(item);
                }
                for (ExtraChemicalStorageVariant variant : ExtraChemicalStorageVariant.values()) {
                    Item item = CHEMICAL_STORAGE_PART.get(variant);
                    output.accept(item);
                }
            }
        });

        ExtraLootFunctions.register();
    }

    public static void init() {
        BlockEntityTypeFactory blockEntityTypeFactory = new BlockEntityTypeFactory() {
            @Nonnull
            @Override
            public <T extends BlockEntity> BlockEntityType<T> create(@Nonnull BlockEntityProvider<T> blockEntityProvider, @Nonnull Block... allowedBlocks) {
                Objects.requireNonNull(blockEntityProvider);
                return new BlockEntityType<>(blockEntityProvider::create, new HashSet<>(Arrays.asList(allowedBlocks)), null);
            }
        };

        ExtendedMenuTypeFactory extendedMenuTypeFactory = new ExtendedMenuTypeFactory() {
            @Nonnull
            @Override
            public <T extends AbstractContainerMenu, D> MenuType<T> create(@Nonnull MenuSupplier<T, D> menuSupplier, @Nonnull StreamCodec<RegistryFriendlyByteBuf, D> streamCodec) {
                return new ExtendedScreenHandlerType<>(menuSupplier::create, streamCodec);
            }
        };

        for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
            String name = variant.getName() + "_item_storage_block";
            ITEM_STORAGE_BLOCK.put(variant, Registry.register(BuiltInRegistries.BLOCK, id(name), new StorageBlock<>(BlockConstants.PROPERTIES, new ExtraItemStorageBlockProvider(variant))));
            ITEM_STORAGE.put(variant, registerItem(name, () -> new ExtraItemStorageBlockItem(ITEM_STORAGE_BLOCK.get(variant), variant)));
            ITEM_STORAGE_TILE.put(variant, Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id(name), blockEntityTypeFactory.create((pos, state) -> RefinedStorageApi.INSTANCE.createStorageBlockEntity(pos, state, new ExtraItemStorageBlockProvider(variant)), ITEM_STORAGE_BLOCK.get(variant))));
            ITEM_STORAGE_CONTAINER.put(variant, Registry.register(BuiltInRegistries.MENU, id(name), extendedMenuTypeFactory.create((syncId, playerInventory, data) -> RefinedStorageApi.INSTANCE.createStorageBlockContainerMenu(syncId, playerInventory.player, data, RefinedStorageApi.INSTANCE.getItemResourceFactory(), Menus.INSTANCE.getItemStorage()), RefinedStorageApi.INSTANCE.getStorageBlockDataStreamCodec())));

            ITEM_STORAGE_PART.put(variant, registerItem(variant.getName() + "_item_storage_part", () -> new Item(new Item.Properties())));
            ITEM_STORAGE_DISK.put(variant, registerItem(variant.getName() + "_item_storage_disk", () -> new ExtraItemStorageDiskItem(variant)));
        }

        for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
            String name = variant.getName() + "_fluid_storage_block";
            FLUID_STORAGE_BLOCK.put(variant, registerBlock(name, () -> new StorageBlock<>(BlockConstants.PROPERTIES, new ExtraFluidStorageBlockProvider(variant))));
            FLUID_STORAGE.put(variant, registerItem(name, () -> new ExtraFluidStorageBlockItem(FLUID_STORAGE_BLOCK.get(variant), variant)));
            FLUID_STORAGE_TILE.put(variant, Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id(name), blockEntityTypeFactory.create((pos, state) -> RefinedStorageApi.INSTANCE.createStorageBlockEntity(pos, state, new ExtraFluidStorageBlockProvider(variant)), FLUID_STORAGE_BLOCK.get(variant))));
            FLUID_STORAGE_CONTAINER.put(variant, Registry.register(BuiltInRegistries.MENU, id(name), extendedMenuTypeFactory.create((syncId, playerInventory, data) -> RefinedStorageApi.INSTANCE.createStorageBlockContainerMenu(syncId, playerInventory.player, data, RefinedStorageApi.INSTANCE.getFluidResourceFactory(), Menus.INSTANCE.getFluidStorage()), RefinedStorageApi.INSTANCE.getStorageBlockDataStreamCodec())));

            FLUID_STORAGE_PART.put(variant, registerItem(variant.getName() + "_fluid_storage_part", () -> new Item(new Item.Properties())));
            FLUID_STORAGE_DISK.put(variant, registerItem(variant.getName() + "_fluid_storage_disk", () -> new ExtraFluidStorageDiskItem(variant)));
        }

        /*if (ModList.get().isLoaded("mekanism") && ModList.get().isLoaded("refinedstorage_mekanism_integration")) {
            for (ExtraChemicalStorageVariant variant : ExtraChemicalStorageVariant.values()) {
                String name = variant.getName() + "_chemical_storage_block";
                CHEMICAL_STORAGE_BLOCK.put(variant, registerBlock(name, () -> new StorageBlock<>(BlockConstants.PROPERTIES, new ExtraChemicalStorageBlockProvider(variant))));
                CHEMICAL_STORAGE.put(variant, registerItem(name, () -> new ExtraChemicalStorageBlockItem(CHEMICAL_STORAGE_BLOCK.get(variant), variant)));
                CHEMICAL_STORAGE_TILE.put(variant, Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id(name), blockEntityTypeFactory.create((pos, state) -> RefinedStorageApi.INSTANCE.createStorageBlockEntity(pos, state, new ExtraChemicalStorageBlockProvider(variant)), CHEMICAL_STORAGE_BLOCK.get(variant))));
                CHEMICAL_STORAGE_CONTAINER.put(variant, Registry.register(BuiltInRegistries.MENU, id(name), extendedMenuTypeFactory.create((syncId, playerInventory, data) -> RefinedStorageApi.INSTANCE.createStorageBlockContainerMenu(syncId, playerInventory.player, data, ChemicalResourceFactory.INSTANCE, com.refinedmods.refinedstorage.mekanism.content.Menus.getChemicalStorage()), RefinedStorageApi.INSTANCE.getStorageBlockDataStreamCodec())));

                CHEMICAL_STORAGE_PART.put(variant, registerItem(variant.getName() + "_chemical_storage_part", () -> new Item(new Item.Properties())));
                CHEMICAL_STORAGE_DISK.put(variant, registerItem(variant.getName() + "_chemical_storage_disk", () -> new ExtraChemicalStorageDiskItem(variant)));
            }
        }*/

        //BLOCKS.register(modBus);
        //ITEMS.register(modBus);
        //TILES.register(modBus);
       // CONTAINERS.register(modBus);
        //RECIPE_SERIALIZERS.register(modBus);
    }

    private static <E extends Item> E registerItem(String s, Supplier<E> o) {
        return Registry.register(BuiltInRegistries.ITEM, id(s), o.get());
    }

    private static <E extends Block> E registerBlock(String s, Supplier<E> o) {
        return Registry.register(BuiltInRegistries.BLOCK, id(s), o.get());
    }
}
