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

package io.github.akashiikun.mavapi.v1.mixin;

import io.github.akashiikun.mavapi.v1.impl.AxolotlBuckets;
import io.github.akashiikun.mavapi.v1.impl.AxolotlRegistry;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
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
		Map<ModelResourceLocation, BakedModel> mavapi$getBakedRegistry();

	}

	@Shadow
	public abstract ModelManager getModelManager();

	@Inject(method = "getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;",
			at = @At("HEAD"), cancellable = true)
	private void mavapi$getBucketModel(ItemStack stack, CallbackInfoReturnable<BakedModel> cir) {
		if (!stack.is(Items.AXOLOTL_BUCKET)) {
			return;
		}

		CompoundTag nbt = stack.get(DataComponents.BUCKET_ENTITY_DATA).copyTag();
		Axolotl.Variant variant = AxolotlRegistry.loadVariant(nbt.getInt(Axolotl.VARIANT_TAG), nbt);
		ResourceLocation id = AxolotlRegistry.getKey(variant);

		if (!AxolotlBuckets.doesModelForBucketExist(id)) {
			return;
		}

		Map<ModelResourceLocation, BakedModel> models = ((ModelManagerAccessor) getModelManager()).mavapi$getBakedRegistry();
		BakedModel bakedModel = models.get(ModelResourceLocation.inventory(id.withPrefix("item/axolotl_bucket_")));

		if (bakedModel != null) {
			cir.setReturnValue(bakedModel);
		}
	}

}
