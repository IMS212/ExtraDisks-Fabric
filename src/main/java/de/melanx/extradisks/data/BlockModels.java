package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.Optional;

public class BlockModels extends FabricModelProvider {
    public BlockModels(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        ResourceLocation parentId = ResourceLocation.tryParse("refinedstorage:block/disk/disk");
        TextureSlot BASE = TextureSlot.create("base");

        ModelTemplate model = new ModelTemplate(Optional.of(parentId), Optional.empty(), BASE);

       // this.getBuilder("block/disk/item_disk").parent(modelFile).texture("base", "block/disk/item_disk");
        //this.getBuilder("block/disk/fluid_disk").parent(modelFile).texture("base", "block/disk/fluid_disk");
        //this.getBuilder("block/disk/chemical_disk").parent(modelFile).texture("base", "block/disk/chemical_disk");

        TextureMapping itemTex = new TextureMapping()
                .put(BASE, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/item_disk"));
        TextureMapping fluidTex = new TextureMapping()
                .put(BASE, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/fluid_disk"));

        Registration.ITEM_STORAGE_BLOCK.forEach((variant, item) -> {
            model.create(item, itemTex, generator.modelOutput);
        });

        Registration.FLUID_STORAGE_BLOCK.forEach((variant, item) -> {
            model.create(item, fluidTex, generator.modelOutput);
        });

        // Write the model JSON using the block-state generator's collector

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        BuiltInRegistries.ITEM.holders().filter(item -> {
            return BuiltInRegistries.ITEM.getKey(item.value()).getNamespace().equalsIgnoreCase(ExtraDisks.MODID);
        }).forEach(holder -> {
            if (holder.value() instanceof BlockItem) {
                //this.generateBlockItemModel(holder);
            } else {
                //this.generateItem(holder);
            }
        });

    }

   /* public BlockModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExtraDisks.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile.UncheckedModelFile modelFile = new ModelFile.UncheckedModelFile("refinedstorage:block/disk/disk");
        this.getBuilder("block/disk/item_disk").parent(modelFile).texture("base", "block/disk/item_disk");
        this.getBuilder("block/disk/fluid_disk").parent(modelFile).texture("base", "block/disk/fluid_disk");
        this.getBuilder("block/disk/chemical_disk").parent(modelFile).texture("base", "block/disk/chemical_disk");
    }*/
}
