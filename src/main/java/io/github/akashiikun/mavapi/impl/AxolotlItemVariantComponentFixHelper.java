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
