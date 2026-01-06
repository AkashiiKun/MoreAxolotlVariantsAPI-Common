// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.impl;

import com.mojang.serialization.Dynamic;

import java.util.Optional;

public class AxolotlItemVariantComponentFixHelper {
	public static <T> Dynamic<T> fixAxolotlBucket(Dynamic<T> data, Dynamic<T> entityData) {
		Optional<Number> optional = entityData.get("Variant").asNumber().result();
		if (optional.isEmpty()) {
			// It's not a number
			Optional<String> optional1 = entityData.get("Variant").asString().result();
			if (optional1.isEmpty()) return data;
			String s = AxolotlMigrationsImpl.migrateName(optional1.get());
			return data.update("minecraft:bucket_entity_data", dynamic -> dynamic.remove("Variant"))
					.set("mavapi:axolotl/variant", data.createString(s));
		} else {
			// Vanilla numerical ids
			String s = AxolotlMigrationsImpl.migrateName(switch (optional.get().intValue()) {
				case 1 -> "minecraft:wild";
				case 2 -> "minecraft:gold";
				case 3 -> "minecraft:cyan";
				case 4 -> "minecraft:blue";
				default -> "minecraft:lucy";
			}); // you can do this but please don't
			return data.update("minecraft:bucket_entity_data", dynamic -> dynamic.remove("Variant"))
					.set("mavapi:axolotl/variant", data.createString(s));
		}
	}
}
