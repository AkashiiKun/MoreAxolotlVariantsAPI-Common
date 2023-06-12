/*
 * Copyright (c) 2021 - 2023 Jab125, LimeAppleBoat & 2022 - 2023 Akashii
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.akashiikun.mavapi.v1.mixin;

import io.github.akashiikun.mavapi.v1.api.AxolotlVariants;
import io.github.akashiikun.mavapi.v1.impl.AxolotlBuckets;
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
import org.spongepowered.asm.mixin.gen.Accessor;
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

	@Mixin(ModelManager.class)
	public interface ModelManagerAccessor {

		@Accessor("bakedRegistry")
		Map<ResourceLocation, BakedModel> getBakedRegistry();

	}


	@Shadow public abstract ModelManager getModelManager();

	@Inject(method = "getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;", at = @At("HEAD"), cancellable = true)
	private void getModel(ItemStack stack, CallbackInfoReturnable<BakedModel> cir) {
		if (stack.is(Items.AXOLOTL_BUCKET)) {
			if (stack.getTag() != null && stack.getTag().contains("Variant", Tag.TAG_STRING)) {
				String variant = stack.getTag().getString("Variant");

				MoreAxolotlVariant metadata = AxolotlVariants.getById(new ResourceLocation(variant));

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
