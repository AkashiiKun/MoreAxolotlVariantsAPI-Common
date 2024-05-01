/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 - 2024 Akashii, 2023 - 2024 KxmischesDomi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.akashiikun.mavapi.v1.mixin;

import io.github.akashiikun.mavapi.v1.api.AxolotlVariants;
import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Debug(export = true)
@Mixin(Axolotl.class)
public abstract class AxolotlEntityMixin extends Animal {
    @Shadow public abstract Axolotl.Variant getVariant();

    @Shadow @Final public static String VARIANT_TAG;

    @Shadow
    public abstract void setVariant(Axolotl.Variant variant);

    private static final EntityDataAccessor<String> mavapi$VARIANT = SynchedEntityData.defineId(Axolotl.class, EntityDataSerializers.STRING);


    protected AxolotlEntityMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    //@Redirect(method = "defineSynchedData", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/syncher/SynchedEntityData$Builder;define(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)Lnet/minecraft/network/syncher/SynchedEntityData$Builder;", ordinal = 0))
    private <T> void mavm$initTrackers(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(mavapi$VARIANT, "minecraft:lucy");
    }

    @Inject(method = "getVariant", at = @At("HEAD"), cancellable = true)
    public void mavm$getVariant(CallbackInfoReturnable<Axolotl.Variant> cir) {
        MoreAxolotlVariant variant1 = AxolotlVariants.getById(new ResourceLocation(this.entityData.get(mavapi$VARIANT)));
        cir.setReturnValue(variant1.getType());
    }

    @Inject(method = "setVariant", at = @At("HEAD"))
    private void mavm$setVariant(Axolotl.Variant variant, CallbackInfo ci) {
        var metadata = ((AxolotlTypeExtension)(Object)variant).mavapi$metadata();
        this.entityData.set(mavapi$VARIANT, metadata.getId().toString());
    }

    @Redirect(method = "saveToBucketTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/CustomData;update(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;)V"))
    private void mavm$saveToBucketTag(DataComponentType<CustomData> dataComponentType, ItemStack itemStack, Consumer<CompoundTag> consumer) {
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, (compoundTag) -> {
            compoundTag.putString("Variant", ((AxolotlTypeExtension)(Object)getVariant()).mavapi$metadata().getId().toString());
            compoundTag.putInt("Age", this.getAge());
            Brain<?> brain = this.getBrain();
            if (brain.hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN)) {
                compoundTag.putLong("HuntingCooldown", brain.getTimeUntilExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN));
            }

        });
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