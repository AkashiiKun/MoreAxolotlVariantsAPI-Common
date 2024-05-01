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

import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
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
import org.spongepowered.asm.mixin.Mixin;
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
	private EntityType<?> entityType;

	@Inject(method = "<init>", at = @At("RETURN"))
	public void onInit(EntityType<?> entityType, Fluid fluid, SoundEvent soundEvent, Item.Properties properties, CallbackInfo ci) {
		this.entityType = entityType;
	}

	@Inject(method = "appendHoverText", at = @At(value = "HEAD"))
	public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
		if (entityType == EntityType.AXOLOTL) {
			CompoundTag nbtCompound = itemStack.get(DataComponents.BUCKET_ENTITY_DATA).copyTag();
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
						list.add(component);
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
