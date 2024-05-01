/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 - 2024 Akashii, 2023 - 2024 KxmischesDomi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.akashiikun.mavapi.mixin;

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

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "net/minecraft/util/profiling/ProfilerFiller.popPush(Ljava/lang/String;)V", ordinal = 2, shift = At.Shift.AFTER))
	private void addModelHook(CallbackInfo info) {
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
