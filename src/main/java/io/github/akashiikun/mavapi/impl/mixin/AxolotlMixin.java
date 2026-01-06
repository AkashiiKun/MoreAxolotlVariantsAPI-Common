// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;
import io.github.akashiikun.mavapi.api.v2.AxolotlVariants;
import io.github.akashiikun.mavapi.api.v2.MavApiAxolotlGroupData;
import io.github.akashiikun.mavapi.api.v2.MavApiDataComponents;
import io.github.akashiikun.mavapi.api.v2.MavApiRegistries;
import io.github.akashiikun.mavapi.impl.extension.AxolotlExtension;
import io.github.akashiikun.mavapi.impl.extension.AxolotlGroupDataExtension;
import io.github.akashiikun.mavapi.impl.init.ModEntityDataSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SyncedDataHolder;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@SuppressWarnings("NullableProblems")
@Mixin(Axolotl.class)
public abstract class AxolotlMixin extends LivingEntity implements AxolotlExtension {
	@Shadow
	private static native boolean useRareVariant(RandomSource random);

	@Unique
	private static @Final @Mutable EntityDataAccessor<Holder<AxolotlVariant>> DATA_VARIANT_ID;

	protected AxolotlMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	// Since initialization order matters, we make sure we always get the same numerical id by replacing Axolotl.DATA_VARIANT with our DATA_VARIANT_ID
	@SuppressWarnings("WrongEntityDataParameterClass")
	@WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/syncher/SynchedEntityData;defineId(Ljava/lang/Class;Lnet/minecraft/network/syncher/EntityDataSerializer;)Lnet/minecraft/network/syncher/EntityDataAccessor;"))
	private static <T> EntityDataAccessor<T> mavapi$clinit(Class<? extends SyncedDataHolder> clazz, EntityDataSerializer<T> serializer, Operation<EntityDataAccessor<T>> original) {
		if (serializer == EntityDataSerializers.INT) {
			DATA_VARIANT_ID = SynchedEntityData.defineId(Axolotl.class, ModEntityDataSerializers.AXOLOTL_VARIANT);
			return null;
		}
		return original.call(clazz, serializer);
	}

	// replace DATA_VARIANT with DATA_VARIANT_ID
	@Redirect(method = "defineSynchedData", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/syncher/SynchedEntityData$Builder;define(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)Lnet/minecraft/network/syncher/SynchedEntityData$Builder;", ordinal = 0))
	<T> SynchedEntityData.Builder mavapi$defineSynchedData(SynchedEntityData.Builder builder, EntityDataAccessor<T> accessor, T value) {
		return builder.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), AxolotlVariants.LUCY));
	}

	@Redirect(method = "addAdditionalSaveData", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ValueOutput;store(Ljava/lang/String;Lcom/mojang/serialization/Codec;Ljava/lang/Object;)V")) // watch this just incase mojang adds more
	<T> void mavapi$addAdditionalSaveData(ValueOutput output, String string, Codec<T> tCodec, T t) {
		VariantUtils.writeVariant(output, this.getVariant());
	}

	// make vanilla getVariant always return the default.
	@Inject(method = "getVariant", at = @At("HEAD"), cancellable = true)
	void mavapi$getVariant(CallbackInfoReturnable<Axolotl.Variant> cir) {
		cir.setReturnValue(Axolotl.Variant.DEFAULT);
	}

	// make vanilla setVariant set the mavapi variant if possible. vanilla setVariant should only be called in mods without mavapi compatibility.
	@Inject(method = "setVariant", at = @At("HEAD"), cancellable = true)
	void mavapi$setVariant(Axolotl.Variant variant, CallbackInfo ci) {
		registryAccess().get(AxolotlVariants.fromVanilla(variant)).ifPresent(this::setVariant);
		ci.cancel();
	}

	// Use mavapi's AxolotlVariant instead
	@Redirect(method = "finalizeSpawn", at = @At(value = "NEW", target = "net/minecraft/world/entity/animal/axolotl/Axolotl$AxolotlGroupData"))
	Axolotl.AxolotlGroupData mavapi$finalizeSpawn(Axolotl.Variant[] types) {
		return MavApiAxolotlGroupData.create(AxolotlVariants.getCommonSpawnVariant(registryAccess(), random), AxolotlVariants.getCommonSpawnVariant(registryAccess(), random));
	}

	// done so a call isn't wasted, also AxolotlGroupData#getVariant() throws an exception when used.
	@Redirect(method = "finalizeSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/axolotl/Axolotl$AxolotlGroupData;getVariant(Lnet/minecraft/util/RandomSource;)Lnet/minecraft/world/entity/animal/axolotl/Axolotl$Variant;"))
	Axolotl.Variant mavapi$finalizeSpawn(Axolotl.AxolotlGroupData instance, RandomSource random) {
		return null;
	}

	// Use mavapi's AxolotlVariant instead
	@Redirect(method = "finalizeSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/axolotl/Axolotl;setVariant(Lnet/minecraft/world/entity/animal/axolotl/Axolotl$Variant;)V"))
	void mavapi$finalizeSpawn(Axolotl instance, Axolotl.Variant variant, @Local(argsOnly = true) SpawnGroupData data) {
		AxolotlVariants.setVariant(instance, MavApiAxolotlGroupData.getVariant((Axolotl.AxolotlGroupData) data, random));
	}

	// Read mavapi's variant, not vanilla's "Variant"
	@Inject(method = "readAdditionalSaveData", at = @At("RETURN"))
	void mavapi$readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
		if (!readLegacyVariant(input)) VariantUtils.readVariant(input, MavApiRegistries.AXOLOTL_VARIANT).ifPresent(this::setVariant);
	}

	// Read vanilla's "Variant", as well as mavapi v1's "Variant"
	@Unique
	private boolean readLegacyVariant(ValueInput input) {
		Axolotl.Variant legacyVariant = input.read("Variant", Axolotl.Variant.LEGACY_CODEC).orElse(null);
		// Handle vanilla's variant
		if (legacyVariant != null) {
			Optional<Holder.Reference<AxolotlVariant>> axolotlVariantReference = registryAccess().get(AxolotlVariants.fromVanilla(legacyVariant));
			axolotlVariantReference.ifPresent(this::setVariant);
			return true; // even if this fails to set, there's a Variant so "variant" shouldn't be used.
		}
		Identifier mavApiV1Variant = input.read("Variant", Identifier.CODEC).orElse(null);
		// Handle mavapi v1's variant
		if (mavApiV1Variant != null) {
			Optional<Holder.Reference<AxolotlVariant>> axolotlVariantReference = registryAccess().lookupOrThrow(MavApiRegistries.AXOLOTL_VARIANT).get(mavApiV1Variant);
			axolotlVariantReference.ifPresent(this::setVariant);
			return true; // even if this fails to set, there's a Variant so "variant" shouldn't be used.
		}
		return false;
	}

	@Inject(method = "get", at = @At("HEAD"), cancellable = true)
	<T> void mavapi$get(DataComponentType<? extends T> component, CallbackInfoReturnable<T> cir) {
		if (component == MavApiDataComponents.AXOLOTL_VARIANT) {
			cir.setReturnValue(castComponentValue(component, this.getVariant()));
		}
	}

	@Inject(method = "applyImplicitComponents", at = @At("HEAD"))
	void mavapi$applyImplicitComponents(DataComponentGetter componentGetter, CallbackInfo ci) {
		this.applyImplicitComponentIfPresent(componentGetter, MavApiDataComponents.AXOLOTL_VARIANT);
	}

	@Inject(method = "applyImplicitComponent", at = @At("HEAD"), cancellable = true)
	<T> void mavapi$applyImplicitComponent(DataComponentType<T> component, T value, CallbackInfoReturnable<Boolean> cir) {
		if (component == MavApiDataComponents.AXOLOTL_VARIANT) {
			this.setVariant(castComponentValue(MavApiDataComponents.AXOLOTL_VARIANT, value));
			cir.setReturnValue(true);
		}
	}

	// Basically identical to vanilla's except that we are using mavapi's AxolotlVariant
	@Inject(method = "getBreedOffspring", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/axolotl/Axolotl;setPersistenceRequired()V"), cancellable = true)
	void mavapi$getBreedOffspring(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir, @Local Axolotl axolotl) {
		Holder<AxolotlVariant> variant;
		if (useRareVariant(this.random)) {
			variant = AxolotlVariants.getRareSpawnVariant(registryAccess(), random);
		} else {
			variant = this.random.nextBoolean() ? this.getVariant() : AxolotlVariants.getVariant((Axolotl) otherParent);
		}
		AxolotlVariants.setVariant(axolotl, variant);
	}

	// Use mavapi's component instead of vanilla's.
	@Redirect(method = "saveToBucketTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copyFrom(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/core/component/DataComponentGetter;)V"))
	<T> void mavapi$saveToBucketTag(ItemStack instance, DataComponentType<T> componentType, DataComponentGetter componentGetter) {
		instance.copyFrom(MavApiDataComponents.AXOLOTL_VARIANT, componentGetter);
	}

	@Override
	public void setVariant(Holder<AxolotlVariant> holder) {
		this.entityData.set(DATA_VARIANT_ID, holder);
	}

	@Override
	public Holder<AxolotlVariant> getVariant() {
		return this.entityData.get(DATA_VARIANT_ID);
	}

	@Mixin(Axolotl.AxolotlGroupData.class)
	public static class AxolotlGroupDataMixin implements AxolotlGroupDataExtension {
		@Unique
		private @Mutable @Final Holder<AxolotlVariant>[] variants; // TODO holder?
		@Inject(method = "<init>", at = @At("CTOR_HEAD"))
		void mavapi$init(Axolotl.Variant[] types, CallbackInfo ci) {
			if (types != null) throw new AssertionError("Use MavApiAxolotlGroupData#create instead!");
		}

		@Override
		public void setVariants(Holder<AxolotlVariant>[] variants) {
			this.variants = variants;
		}

		@Override
		public Holder<AxolotlVariant> getVariant(RandomSource random) {
			return this.variants[random.nextInt(this.variants.length)];
		}
	}

	// Needed since they have clashing names
	@Mixin(Axolotl.AxolotlGroupData.class)
	public static class AxolotlGroupData2Mixin {
		/**
		 * @author Jab125
		 * @reason Make our intentions clear
		 */
		@Overwrite
		public Axolotl.Variant getVariant(RandomSource random) {
			throw new AssertionError("Use MavApiAxolotlGroupData#getVariant instead!");
		}
	}
}
