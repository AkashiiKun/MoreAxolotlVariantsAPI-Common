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

import io.github.akashiikun.mavapi.v1.api.ModdedAxolotlVariant;
import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Locale;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Mixin(MobBucketItem.class)
public abstract class MobBucketItemMixin {

	@Shadow @Final private EntityType<?> type;

	@Inject(method = "appendHoverText", at = @At(value = "HEAD"))
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context, CallbackInfo ci) {
		if (type == EntityType.AXOLOTL) {
			CompoundTag nbtCompound = stack.getTag();
			if (nbtCompound != null && nbtCompound.contains(Axolotl.VARIANT_TAG, Tag.TAG_STRING)) {
				String variantIdentifier = nbtCompound.getString(Axolotl.VARIANT_TAG);

				for (Axolotl.Variant variant : Axolotl.Variant.values()) {

					MoreAxolotlVariant metadata = ((AxolotlTypeExtension) (Object) variant).mavapi$metadata();

					ResourceLocation id = metadata.getId();
					if (id.equals(new ResourceLocation(variantIdentifier))) {

						int age = nbtCompound.getInt("Age");

						MutableComponent component = Component.translatable("mavapi.bucket.format",
								age < 0 ? Component.translatable("mavapi.bucket.translation.baby") : Component.translatable("mavapi.bucket.translation.adult"),
								Component.translatable(String.format("mavapi.variant.%s.%s", id.getNamespace(), id.getPath())),
								Component.translatable(String.format("mavapi.mod.%s", id.getNamespace()))
						);
						component.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));
						tooltip.add(component);
						break;
					}
				}

			}
		}
	}

}
