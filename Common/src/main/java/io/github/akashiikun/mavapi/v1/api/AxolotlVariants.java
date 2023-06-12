package io.github.akashiikun.mavapi.v1.api;

import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class AxolotlVariants {

	public static final Map<ResourceLocation, MoreAxolotlVariant> BY_ID = new HashMap<>();

	public static MoreAxolotlVariant getById(ResourceLocation id) {
		return BY_ID.get(id);
	}

}
