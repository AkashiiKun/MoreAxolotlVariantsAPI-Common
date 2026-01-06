// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
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
