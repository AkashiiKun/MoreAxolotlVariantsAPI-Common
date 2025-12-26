package io.github.akashiikun.mavapi.api.v2;

import net.minecraft.core.Holder;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
//? if fabric {
import net.minecraft.core.Registry;
//?}

import java.util.function.Supplier;

// deferred only exists because NeoForge doesn't like using the registry directly
@SuppressWarnings("NullableProblems")
public class MavApiDataComponents {
	//? if fabric {
	public static final DataComponentType<Holder<AxolotlVariant>> AXOLOTL_VARIANT = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.parse("mavapi:axolotl/variant"), DataComponentType.<Holder<AxolotlVariant>>builder().persistent(AxolotlVariant.CODEC).networkSynchronized(AxolotlVariant.STREAM_CODEC).build());
	// The only reason you would use this is in a multloader environment
	public static final Supplier<DataComponentType<Holder<AxolotlVariant>>> DEFERRED_AXOLOTL_VARIANT = () -> MavApiDataComponents.AXOLOTL_VARIANT;
	//?} else if neoforge {
	/*// this is because neoforge sucks
	public static DataComponentType<Holder<AxolotlVariant>> AXOLOTL_VARIANT = null;
	public static final Supplier<DataComponentType<Holder<AxolotlVariant>>> DEFERRED_AXOLOTL_VARIANT = () -> (DataComponentType<Holder<AxolotlVariant>>) BuiltInRegistries.DATA_COMPONENT_TYPE.getOptional(Identifier.parse("mavapi:axolotl/variant")).orElseThrow();
	*///?}
}
