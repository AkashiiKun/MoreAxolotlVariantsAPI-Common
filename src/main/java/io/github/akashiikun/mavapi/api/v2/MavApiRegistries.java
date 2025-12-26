package io.github.akashiikun.mavapi.api.v2;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import static net.minecraft.resources.ResourceKey.createRegistryKey;

@SuppressWarnings("NullableProblems")
public class MavApiRegistries {
	public static final ResourceKey<Registry<AxolotlVariant>> AXOLOTL_VARIANT = createRegistryKey(Identifier.parse("mavapi:axolotl_variant"));
}
