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

import io.github.akashiikun.mavapi.v1.impl.AxolotlRegistry;
import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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
	@Shadow
	private EntityType<?> type;

	@Inject(method = "appendHoverText", at = @At(value = "HEAD"))
	private void mavapi$appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
		if (type != EntityType.AXOLOTL) {
			return;
		}

		CompoundTag nbt = itemStack.get(DataComponents.BUCKET_ENTITY_DATA).copyTag();
		if (nbt == null) {
			return;
		}

		Axolotl.Variant variant = AxolotlRegistry.loadVariant(nbt.getInt(Axolotl.VARIANT_TAG), nbt);

		ResourceLocation id = ((AxolotlTypeExtension) (Object) variant).mavapi$getId();
		int age = nbt.getInt("Age");

		MutableComponent component = Component.translatable("mavapi.bucket.format",
				age < 0 ? Component.translatable("mavapi.bucket.translation.baby") : Component.translatable("mavapi.bucket.translation.adult"),
				translateOrFormat(String.format("mavapi.variant.%s.%s", id.getNamespace(), id.getPath()), id.getPath()),
				translateOrFormat(String.format("mavapi.mod.%s", id.getNamespace()), id.getNamespace())
		);
		component.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));
		list.add(component);
	}

	@Unique
	private static MutableComponent translateOrFormat(String translation, String toFormat) {
		MutableComponent component = Component.translatable(translation);
		if (!I18n.exists(translation)) {
			component = Component.literal(formatName(toFormat));
		}
		return component;
	}

	@Unique
	private static String formatName(String s) {
		s = s.replace("_", " ");
		s = String.valueOf(s.charAt(0)).toUpperCase(Locale.ROOT) + s.substring(1);
		return s;
	}

}
