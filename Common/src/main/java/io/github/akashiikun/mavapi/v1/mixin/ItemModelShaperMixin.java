package io.github.akashiikun.mavapi.v1.mixin;

import io.github.akashiikun.mavapi.v1.api.AxolotlBuckets;
import io.github.akashiikun.mavapi.v1.api.ModdedAxolotlVariant;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Mixin(ItemModelShaper.class)
public abstract class ItemModelShaperMixin {

	@Shadow public abstract ModelManager getModelManager();

	@Inject(method = "getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;", at = @At("HEAD"), cancellable = true)
	private void getModel(ItemStack stack, CallbackInfoReturnable<BakedModel> cir) {
		if (stack.is(Items.AXOLOTL_BUCKET)) {
			if (stack.getTag() != null && stack.getTag().contains("Variant", Tag.TAG_STRING)) {
				String variant = stack.getTag().getString("Variant");

				MoreAxolotlVariant metadata = ModdedAxolotlVariant.getMetadataById(new ResourceLocation(variant));

				if (AxolotlBuckets.doesModelForBucketExist(metadata.getId())) {
					Map<ResourceLocation, BakedModel> models = ((ModelManagerAccessor) getModelManager()).getBakedRegistry();
					ResourceLocation resourceLocation = new ResourceLocation(metadata.getId().getNamespace(), String.format("item/axolotl_bucket_%s", metadata.getId().getPath()));
					BakedModel bakedModel = models.get(resourceLocation);
					if (bakedModel != null) cir.setReturnValue(bakedModel);
				}


			}
		}
	}

}
