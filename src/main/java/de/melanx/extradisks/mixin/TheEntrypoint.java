package de.melanx.extradisks.mixin;

import com.refinedmods.refinedstorage.common.api.RefinedStorageClientApi;
import com.refinedmods.refinedstorage.fabric.ModInitializerImpl;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import de.melanx.extradisks.content.chemical.ExtraChemicalStorageDiskItem;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.content.item.ExtraItemStorageDiskItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModInitializerImpl.class, remap = false)
public class TheEntrypoint {
    @Inject(method = "onInitialize", at = @At("RETURN"))
    private void runExtraDisks(CallbackInfo ci) {
        new ExtraDisks().onInitialize();
    }
}
