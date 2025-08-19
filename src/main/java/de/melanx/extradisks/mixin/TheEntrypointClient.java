package de.melanx.extradisks.mixin;

import com.refinedmods.refinedstorage.common.api.RefinedStorageClientApi;
import com.refinedmods.refinedstorage.fabric.ClientModInitializerImpl;
import com.refinedmods.refinedstorage.fabric.ModInitializerImpl;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import de.melanx.extradisks.content.chemical.ExtraChemicalStorageDiskItem;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.content.item.ExtraItemStorageDiskItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientModInitializerImpl.class, remap = false)
public class TheEntrypointClient {
    @Inject(method = "onInitializeClient", at = @At("RETURN"))
    private void runExtraDisks(CallbackInfo ci) {
        for (ExtraItemStorageDiskItem value : Registration.ITEM_STORAGE_DISK.values()) {
            RefinedStorageClientApi.INSTANCE.registerDiskModel(
                    value.asItem(), ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/item_disk")
            );
        }

        for (ExtraFluidStorageDiskItem value : Registration.FLUID_STORAGE_DISK.values()) {
            RefinedStorageClientApi.INSTANCE.registerDiskModel(
                    value.asItem(), ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/fluid_disk")
            );
        }

        if (FabricLoader.getInstance().isModLoaded("mekanism") && FabricLoader.getInstance().isModLoaded("refinedstorage_mekanism_integration")) {
            for (ExtraChemicalStorageDiskItem value : Registration.CHEMICAL_STORAGE_DISK.values()) {
                RefinedStorageClientApi.INSTANCE.registerDiskModel(
                        value.asItem(), ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/chemical_disk")
                );
            }
        }
    }
}
