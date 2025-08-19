package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DataCreator implements DataGeneratorEntrypoint {


    @Override
    public void onInitializeDataGenerator(FabricDataGenerator provider) {
        FabricDataGenerator.Pack generator = provider.createPack();
        CompletableFuture<HolderLookup.Provider> lookupProvider = provider.getRegistries();

        generator.addProvider(ModTags.BlockTags::new);
        generator.addProvider(ModTags.ItemTags::new);

        generator.addProvider(Recipes::new);
        generator.addProvider(ExtraAdvancementProvider::new);
        generator.addProvider(ExtraLootTables::new);
        //generator.addProvider(BlockStates::new);
        generator.addProvider(BlockModels::new);

       // generator.addProvider(server, new ModTags.ItemTags(generator, lookupProvider, blockTagsProvider.contentsGetter(), helper));
       // generator.addProvider(server, new LootTableProvider(generator, Set.of(), List.of(
       //         new LootTableProvider.SubProviderEntry(ExtraLootTables::new, LootContextParamSets.BLOCK)
       // ), lookupProvider));

      //  boolean client = event.includeClient();
      //  generator.addProvider(client, new ModItemModels(generator, helper));
       // generator.addProvider(client, new BlockStates(generator, helper));
       // generator.addProvider(client, new BlockModels(generator, helper));*/
    }
}
