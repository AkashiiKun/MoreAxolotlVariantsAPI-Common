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

import io.github.akashiikun.mavapi.v1.impl.AxolotlBuckets;
import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

	@Shadow @Final public static ModelResourceLocation MISSING_MODEL_LOCATION;

	@Shadow public abstract UnbakedModel getModel(ResourceLocation resourceLocation);

	@Shadow @Final private Map<ResourceLocation, UnbakedModel> unbakedCache;

	@Shadow @Final private Map<ResourceLocation, UnbakedModel> topLevelModels;

	@Inject(method = "loadTopLevel", at = @At("TAIL"))
	private void addModelHook(ModelResourceLocation id, CallbackInfo info) {
		if (id == MISSING_MODEL_LOCATION) {

			for (Axolotl.Variant variant : Axolotl.Variant.values()) {
				MoreAxolotlVariant metadata = ((AxolotlTypeExtension) (Object) variant).mavapi$metadata();

				ResourceLocation variantId = metadata.getId();

				if (AxolotlBuckets.doesModelForBucketExist(variantId)) {
					ResourceLocation modelLocation = new ResourceLocation(variantId.getNamespace(), String.format("item/axolotl_bucket_%s", variantId.getPath()));
					UnbakedModel unbakedModel = getModel(modelLocation);
					this.unbakedCache.put(modelLocation, unbakedModel);
					this.topLevelModels.put(modelLocation, unbakedModel);
				}

			}


		}

	}

}
