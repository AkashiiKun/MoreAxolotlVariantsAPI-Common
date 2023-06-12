/*
 * Copyright (c) 2021 - 2023 Jab125, LimeAppleBoat & 2022 - 2023 Akashii, 2023 KxmischesDomi
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

import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.plaf.SpinnerUI;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Mixin(MobBucketItem.class)
public abstract class MobBucketItemMixin {

	private EntityType<?> entityType;

	@Inject(method = "<init>", at = @At("RETURN"))
	public void onInit(EntityType<?> entityType, Fluid fluid, SoundEvent soundEvent, Item.Properties properties, CallbackInfo ci) {
		this.entityType = entityType;
	}

	@Inject(method = "appendHoverText", at = @At(value = "HEAD"))
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context, CallbackInfo ci) {
		if (entityType == EntityType.AXOLOTL) {
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
								translateOrFormat(String.format("mavapi.variant.%s.%s", id.getNamespace(), id.getPath()), id.getPath()),
								translateOrFormat(String.format("mavapi.mod.%s", id.getNamespace()), id.getNamespace())
						);
						component.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));
						tooltip.add(component);
						break;
					}
				}

			}
		}
	}

	private MutableComponent translateOrFormat(String translation, String toFormat) {
		MutableComponent component = Component.translatable(translation);
		if (!I18n.exists(translation)) {
			component = Component.literal(formatName(toFormat));
		}
		return component;
	}

	private String formatName(String s) {
		s =  s.replace("_", " ");
		s = String.valueOf(s.charAt(0)).toUpperCase(Locale.ROOT) + s.substring(1);
		return s;
	}

}
