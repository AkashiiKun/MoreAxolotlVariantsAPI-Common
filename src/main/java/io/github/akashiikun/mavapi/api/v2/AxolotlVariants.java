//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.github.akashiikun.mavapi.api.v2;

import io.github.akashiikun.mavapi.impl.extension.AxolotlExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

import java.util.Map;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class AxolotlVariants {
    public static final ResourceKey<AxolotlVariant> LUCY = createKey(Identifier.withDefaultNamespace("lucy"));
    public static final ResourceKey<AxolotlVariant> WILD = createKey(Identifier.withDefaultNamespace("wild"));
    public static final ResourceKey<AxolotlVariant> GOLD = createKey(Identifier.withDefaultNamespace("gold"));
	public static final ResourceKey<AxolotlVariant> CYAN = createKey(Identifier.withDefaultNamespace("cyan"));
	public static final ResourceKey<AxolotlVariant> BLUE = createKey(Identifier.withDefaultNamespace("blue"));
	public static final ResourceKey<AxolotlVariant> DEFAULT = LUCY;

    private static ResourceKey<AxolotlVariant> createKey(Identifier identifier) {
        return ResourceKey.create(MavApiRegistries.AXOLOTL_VARIANT, identifier);
    }

	public static Holder<AxolotlVariant> getCommonSpawnVariant(RegistryAccess registryAccess, RandomSource random) {
		return getSpawnVariant(registryAccess, random, true);
	}

	public static Holder<AxolotlVariant> getRareSpawnVariant(RegistryAccess registryAccess, RandomSource random) {
		return getSpawnVariant(registryAccess, random, false);
	}

	private static Holder<AxolotlVariant> getSpawnVariant(RegistryAccess registryAccess, RandomSource random, boolean common) {
		//noinspection unchecked
		Holder<AxolotlVariant>[] array = registryAccess.lookupOrThrow(MavApiRegistries.AXOLOTL_VARIANT).entrySet().stream().filter(variant -> common != variant.getValue().rare()).map(a -> registryAccess.getOrThrow(a.getKey())).toArray(Holder.Reference[]::new);
		if (false) for (Map.Entry<ResourceKey<AxolotlVariant>, AxolotlVariant> resourceKeyAxolotlVariantEntry : registryAccess.lookupOrThrow(MavApiRegistries.AXOLOTL_VARIANT).entrySet()) {
			System.err.println(resourceKeyAxolotlVariantEntry);
		}
		return Util.getRandom(array, random);
	}

	public static ResourceKey<AxolotlVariant> fromVanilla(Axolotl.Variant variant) {
		return switch (variant) {
			case LUCY -> LUCY;
			case GOLD -> GOLD;
			case WILD -> WILD;
			case CYAN -> CYAN;
			case BLUE -> BLUE;
		};
	}

	public static void setVariant(Axolotl axolotl, Holder<AxolotlVariant> variantHolder) {
		((AxolotlExtension) axolotl).setVariant(variantHolder);
	}

	public static Holder<AxolotlVariant> getVariant(Axolotl axolotl) {
		return ((AxolotlExtension) axolotl).getVariant();
	}

	public static Optional<? extends Holder<AxolotlVariant>> getBucketVariant(RegistryAccess access, DataComponentGetter dataComponentGetter) {
		{
			Axolotl.Variant variant = dataComponentGetter.get(DataComponents.AXOLOTL_VARIANT);
			if (variant != null) return access.get(fromVanilla(variant));
		}
		{
			Holder<AxolotlVariant> variant = dataComponentGetter.get(MavApiDataComponents.AXOLOTL_VARIANT);
			if (variant != null) return Optional.of(variant);
		}
		return Optional.empty();
	}

	public static boolean hasAxolotlVariantComponent(DataComponentGetter dataComponentGetter) {
		return dataComponentGetter.get(DataComponents.AXOLOTL_VARIANT) != null || dataComponentGetter.get(MavApiDataComponents.AXOLOTL_VARIANT) != null;
	}
}
