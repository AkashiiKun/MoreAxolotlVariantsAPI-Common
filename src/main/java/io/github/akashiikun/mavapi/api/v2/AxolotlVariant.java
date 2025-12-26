//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.github.akashiikun.mavapi.api.v2;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

import java.util.List;

public record AxolotlVariant(ClientAsset.ResourceTexture assetInfo, SpawnPrioritySelectors spawnConditions, boolean rare) implements PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<AxolotlVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(AxolotlVariant::assetInfo), SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(AxolotlVariant::spawnConditions), Codec.BOOL.optionalFieldOf("rare", false).forGetter(AxolotlVariant::rare)).apply(instance, AxolotlVariant::new));
	// this is from the server to the client?
	public static final Codec<AxolotlVariant> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(ClientAsset.ResourceTexture.DEFAULT_FIELD_CODEC.forGetter(AxolotlVariant::assetInfo)).apply(instance, AxolotlVariant::new));
	public static final Codec<Holder<AxolotlVariant>> CODEC;
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<AxolotlVariant>> STREAM_CODEC;

	private AxolotlVariant(ClientAsset.ResourceTexture assetInfo) {
		this(assetInfo, SpawnPrioritySelectors.EMPTY, false);
	}

	public List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}

	static {
		CODEC = RegistryFixedCodec.create(MavApiRegistries.AXOLOTL_VARIANT);
		STREAM_CODEC = ByteBufCodecs.holderRegistry(MavApiRegistries.AXOLOTL_VARIANT);
	}
}
