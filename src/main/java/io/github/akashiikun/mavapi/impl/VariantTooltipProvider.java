// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.impl;

import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;
import io.github.akashiikun.mavapi.api.v2.AxolotlVariants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

public final class VariantTooltipProvider implements TooltipProvider {
	private final @Nullable Holder<AxolotlVariant> variantHolder;
	private final boolean legacy;

	public VariantTooltipProvider(Holder<AxolotlVariant> variantHolder) {
		this.variantHolder = variantHolder;
		this.legacy = false;
	}

	public VariantTooltipProvider(@Nullable HolderLookup.Provider access, Axolotl.Variant legacyVariant) {
		this.variantHolder = access == null ? null : access.get(AxolotlVariants.fromVanilla(legacyVariant)).orElse(null);
		this.legacy = true;
	}

	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
		Optional<ResourceKey<AxolotlVariant>> axolotlVariantResourceKey = variantHolder == null ? Optional.empty() : variantHolder.unwrapKey();
		MutableComponent component;
		@SuppressWarnings("MixinInnerClass")
		enum Age {
			ADULT("mavapi.bucket.translation.adult"),
			BABY("mavapi.bucket.translation.baby");

			private final String translationKey;

			Age(String translationKey) {
				this.translationKey = translationKey;
			}
		}
		CustomData customData = componentGetter.get(DataComponents.BUCKET_ENTITY_DATA);
		if (customData != null && axolotlVariantResourceKey.isPresent()) {
			Optional<Integer> ageInt = customData.copyTag().getInt("Age");
			Age age = ageInt.map(nbtAge -> nbtAge < 0 ? Age.BABY : Age.ADULT).orElse(Age.ADULT);
			Identifier id = MultiversionHelper.toIdentifier(axolotlVariantResourceKey.get());
			component = Component.translatable("mavapi.bucket.format",
					Component.translatable(age.translationKey),
					translateOrFormat(String.format("mavapi.variant.%s.%s", id.getNamespace(), id.getPath()), id.getPath()),
					translateOrFormat(String.format("mavapi.mod.%s", id.getNamespace()), id.getNamespace())
			);
		} else {
			component = Component.literal("missingno");
		}

		component.setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));
		if (legacy) component.withStyle(style -> style.withItalic(true));
		tooltipAdder.accept(component);
	}

	private MutableComponent translateOrFormat(String translation, String toFormat) {
		MutableComponent component = Component.translatable(translation);
		if (!I18n.exists(translation)) {
			component = Component.literal(formatName(toFormat));
		}
		return component;
	}

	private String formatName(String s) {
		s = s.replace("_", " ");
		s = String.valueOf(s.charAt(0)).toUpperCase(Locale.ROOT) + s.substring(1);
		return s;
	}
}
