/*
 * Copyright (c) 2021 - 2023 Jab125, LimeAppleBoat & 2022 - 2023 Akashii, 2023 KxmischesDomi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.akashiikun.mavapi.v1.mixin;

import io.github.akashiikun.mavapi.v1.api.AxolotlVariants;
import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Axolotl.class)
public abstract class AxolotlEntityMixin extends LivingEntity {
    @Shadow public abstract Axolotl.Variant getVariant();

    @Shadow @Final public static String VARIANT_TAG;

    @Shadow protected abstract void setVariant(Axolotl.Variant variant);

    @Unique
    private static final EntityDataAccessor<String> mavapi$VARIANT = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.STRING);


    protected AxolotlEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void mavm$initTrackers(CallbackInfo ci) {
        this.entityData.define(mavapi$VARIANT, "minecraft:lucy");
    }

    @Inject(method = "getVariant", at = @At("HEAD"), cancellable = true)
    public void getVariant(CallbackInfoReturnable<Axolotl.Variant> cir) {
        MoreAxolotlVariant variant1 = AxolotlVariants.getById(new ResourceLocation(this.entityData.get(mavapi$VARIANT)));
        cir.setReturnValue(variant1.getType());
    }

    @Inject(method = "setVariant", at = @At("HEAD"))
    private void setVariant(Axolotl.Variant variant, CallbackInfo ci) {
        var metadata = ((AxolotlTypeExtension)(Object)variant).mavapi$metadata();
        this.entityData.set(mavapi$VARIANT, metadata.getId().toString());
    }

    @Redirect(method = "saveToBucketTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putInt(Ljava/lang/String;I)V"))
    private void mavm$saveToBucketTag(CompoundTag instance, String key, int value) {
        instance.putString(key, ((AxolotlTypeExtension)(Object)getVariant()).mavapi$metadata().getId().toString());
    }

    @Redirect(method = "loadFromBucketTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/axolotl/Axolotl;setVariant(Lnet/minecraft/world/entity/animal/axolotl/Axolotl$Variant;)V"))
    private void mavm$loadFromBucketTag(Axolotl instance, Axolotl.Variant variant) {

    }

    @Redirect(method = "loadFromBucketTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getInt(Ljava/lang/String;)I"))
    private int mavm$loadFromBucketTag2(CompoundTag instance, String key) {
        return 0;
    }

    @Inject(method = "loadFromBucketTag", at = @At(value = "RETURN"))
    private void mavm$loadFromBucketTag(CompoundTag nbt, CallbackInfo ci) {
        try {
            replaceLegacyId(nbt);
            MoreAxolotlVariant variant1 = AxolotlVariants.getById(new ResourceLocation(nbt.getString(VARIANT_TAG)));
            this.setVariant(variant1.getType());
        } catch (Exception e) {
            e.printStackTrace();
            this.setVariant(Axolotl.Variant.LUCY);
        }
    }

    @Redirect(method = "addAdditionalSaveData", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;putInt(Ljava/lang/String;I)V"))
    private void mavm$addAdditionalSaveData(CompoundTag instance, String key, int value) {
        instance.putString(VARIANT_TAG, ((AxolotlTypeExtension)(Object)this.getVariant()).mavapi$metadata().getId().toString());
    }

    @Unique
    private CompoundTag nbt;
    @Inject(method = "readAdditionalSaveData", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Animal;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.AFTER))
    private void mavm$readAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        this.nbt = nbt;
    }

    @Redirect(method = "readAdditionalSaveData", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/axolotl/Axolotl;setVariant(Lnet/minecraft/world/entity/animal/axolotl/Axolotl$Variant;)V"))
    private void mavm$readAdditionalSaveData(Axolotl instance, Axolotl.Variant variant) {
        try {
            replaceLegacyId(nbt);
        } catch (Exception e) {
            e.printStackTrace();
            nbt.putString(VARIANT_TAG, "minecraft:lucy");
        }

        if (nbt.contains(VARIANT_TAG, Tag.TAG_STRING)) {
            MoreAxolotlVariant variant1 = AxolotlVariants.getById(ResourceLocation.tryParse(nbt.getString(VARIANT_TAG)));
            if (variant1 != null) {
                this.setVariant(variant1.getType());
            }
        }


    }

    private void replaceLegacyId(CompoundTag nbt) {
        if (nbt.contains(VARIANT_TAG, Tag.TAG_INT)) {
            int i = nbt.getInt(VARIANT_TAG);
            nbt.remove(VARIANT_TAG);

            for (Axolotl.Variant variant : Axolotl.Variant.values()) {
                MoreAxolotlVariant metadata = ((AxolotlTypeExtension) (Object) variant).mavapi$metadata();
                if (metadata.getLegacyIndex() == i) {
                    nbt.putString(VARIANT_TAG, metadata.getId().toString());
                    return;
                }
            }
            nbt.putString(VARIANT_TAG, "minecraft:lucy");
        }
    }

}