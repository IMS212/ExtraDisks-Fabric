package de.melanx.extradisks.data;

import com.refinedmods.refinedstorage.common.storage.storageblock.StorageBlock;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import de.melanx.extradisks.loottable.ExtraStorageBlockLootFunction;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ExtraLootTables extends FabricBlockLootTableProvider {

    protected ExtraLootTables(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }



    @Override
    public void generate() {
        BuiltInRegistries.BLOCK.stream().filter(block -> {
            return BuiltInRegistries.BLOCK.getKey(block).getNamespace().equalsIgnoreCase(ExtraDisks.MODID);
        }).forEach(block -> {
            if (block instanceof StorageBlock) {
                this.genBlockItemLootTableWithFunction(block, new ExtraStorageBlockLootFunction.Builder());
            } else {
                this.dropSelf(block);
            }
        });
    }

    private void genBlockItemLootTableWithFunction(Block block, ExtraStorageBlockLootFunction.Builder builder) {
        this.add(block, LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block)
                                .apply(builder)
                                .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                        .include(DataComponents.CUSTOM_NAME))
                        )));
    }
}
