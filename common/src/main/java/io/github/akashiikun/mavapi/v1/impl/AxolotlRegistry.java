/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2025 Ampflower
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

package io.github.akashiikun.mavapi.v1.impl;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

/**
 * @author Ampflower
 * @since 2.0.0
 **/
public final class AxolotlRegistry {
	public static final ResourceKey<Registry<Axolotl.Variant>> AXOLOTL_VARIANTS_KEY
			= Pivot.registry("axolotl/variants");

	public static final Registry<Axolotl.Variant> AXOLOTL_VARIANTS
			= Pivot.INSTANCE.defaultRegistry(AXOLOTL_VARIANTS_KEY, ResourceLocation.withDefaultNamespace("lucy"));

	public static ResourceLocation getKey(Axolotl.Variant variant) {
		return toExt(variant).mavapi$getId();
	}

	public static ResourceLocation getKey(int variant) {
		return AXOLOTL_VARIANTS.getHolder(variant).orElseThrow().key().location();
	}

	public static Axolotl.Variant getValue(int variant) {
		return AXOLOTL_VARIANTS.getHolder(variant).orElseThrow().value();
	}

	public static Axolotl.Variant get(String variant) {
		return AXOLOTL_VARIANTS.get(ResourceLocation.tryParse(variant));
	}

	public static AxolotlTypeExtension toExt(Object object) {
		return (AxolotlTypeExtension) object;
	}

	public static Axolotl.Variant loadVariant(int id, CompoundTag nbt) {
		if (nbt.contains(Axolotl.VARIANT_TAG, Tag.TAG_STRING)) {
			return AxolotlRegistry.get(nbt.getString(Axolotl.VARIANT_TAG));
		}

		for (final var type : Axolotl.Variant.values()) {
			final int legacyId = toExt(type).mavapi$getLegacyId();

			if (legacyId == id) {
				return type;
			}
		}

		return Axolotl.Variant.LUCY;
	}

	static void init() {
	}
}
