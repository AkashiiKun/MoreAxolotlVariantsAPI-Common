/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 AkashiiKun
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.akashiikun.mavapi.v1.impl.mixin;

import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxolotlEntity.class)
public abstract class AxolotlEntityMixin extends LivingEntity {
    @Shadow public abstract AxolotlEntity.Variant getVariant();

    @Shadow @Final public static String VARIANT_KEY;

    @Shadow protected abstract void setVariant(AxolotlEntity.Variant variant);

    @Unique
    private static final TrackedData<String> mavapi$VARIANT = DataTracker.registerData(AxolotlEntity.class, TrackedDataHandlerRegistry.STRING);


    protected AxolotlEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void mavml$initTrackers(CallbackInfo ci) {
        this.dataTracker.startTracking(mavapi$VARIANT, "minecraft:lucy");
    }

    @Inject(method = "getVariant", at = @At("HEAD"), cancellable = true)
    public void getVariant(CallbackInfoReturnable<AxolotlEntity.Variant> cir) {
        for (AxolotlEntity.Variant variant : AxolotlEntity.Variant.VARIANTS) {
            var metadata = ((AxolotlTypeExtension)(Object)variant).mavapi$metadata();
            if (metadata.getId().toString().equals(this.dataTracker.get(mavapi$VARIANT))) {
                cir.setReturnValue(variant);
                break;
            }
        }
    }

    @Inject(method = "setVariant", at = @At("HEAD"))
    private void setVariant(AxolotlEntity.Variant variant, CallbackInfo ci) {
        var metadata = ((AxolotlTypeExtension)(Object)variant).mavapi$metadata();
        this.dataTracker.set(mavapi$VARIANT, metadata.getId().toString());
    }

    @Redirect(method = "copyDataToStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putInt(Ljava/lang/String;I)V", ordinal = 0))
    private void mavm$copyDataToStack(NbtCompound instance, String key, int value) {
        instance.putString(key, ((AxolotlTypeExtension)(Object)getVariant()).mavapi$metadata().getId().toString());
    }

    @Redirect(method = "copyDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AxolotlEntity;setVariant(Lnet/minecraft/entity/passive/AxolotlEntity$Variant;)V", ordinal = 0))
    private void mavml$copyDataFromNbt(AxolotlEntity instance, AxolotlEntity.Variant variant) {

    }

    @Redirect(method = "copyDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getInt(Ljava/lang/String;)I", ordinal = 0))
    private int mavml$copyDataFromNbt2(NbtCompound instance, String key) {
        return 0;
    }

    @Inject(method = "copyDataFromNbt", at = @At(value = "RETURN"))
    private void mavml$copyDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        try {
            if (nbt.contains(VARIANT_KEY, NbtElement.INT_TYPE)) {
                var i = nbt.getInt(VARIANT_KEY);
                if (i >= 0 && i < AxolotlEntity.Variant.VARIANTS.length) {
                    nbt.remove(VARIANT_KEY);
                    nbt.putString(VARIANT_KEY, ((AxolotlTypeExtension) (Object) AxolotlEntity.Variant.VARIANTS[i]).mavapi$metadata().getId().toString());
                } else {
                    nbt.putString(VARIANT_KEY, "minecraft:lucy");
                }
            }
            for (AxolotlEntity.Variant variant : AxolotlEntity.Variant.VARIANTS) {
                if (((AxolotlTypeExtension) (Object) variant).mavapi$metadata().getId().equals(new Identifier(nbt.getString(VARIANT_KEY)))) {
                    this.setVariant(variant);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setVariant(AxolotlEntity.Variant.LUCY);
        }
    }

    @Redirect(method = "writeCustomDataToNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putInt(Ljava/lang/String;I)V", ordinal = 0))
    private void mavml$writeCustomDataToNbt(NbtCompound instance, String key, int value) {
        instance.putString(VARIANT_KEY, ((AxolotlTypeExtension)(Object)this.getVariant()).mavapi$metadata().getId().toString());
    }

    @Unique
    private NbtCompound nbt;
    @Inject(method = "readCustomDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", shift = At.Shift.AFTER))
    private void mavml$readCustomDataFromNbtCapture(NbtCompound nbt, CallbackInfo ci) {
        this.nbt = nbt;
    }

    @Redirect(method = "readCustomDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AxolotlEntity;setVariant(Lnet/minecraft/entity/passive/AxolotlEntity$Variant;)V", ordinal = 0))
    private void mavml$readCustomDataFromNbt(AxolotlEntity instance, AxolotlEntity.Variant variant) {
        try {
            if (nbt.contains(VARIANT_KEY, NbtElement.INT_TYPE)) {
                var i = nbt.getInt(VARIANT_KEY);
                if (i >= 0 && i < AxolotlEntity.Variant.VARIANTS.length) {
                    nbt.remove(VARIANT_KEY);
                    nbt.putString(VARIANT_KEY, ((AxolotlTypeExtension) (Object) AxolotlEntity.Variant.VARIANTS[i]).mavapi$metadata().getId().toString());
                } else {
                    nbt.putString(VARIANT_KEY, "minecraft:lucy");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            nbt.putString(VARIANT_KEY, "minecraft:lucy");
        }
        for (AxolotlEntity.Variant variant1 : AxolotlEntity.Variant.VARIANTS) {
            if (((AxolotlTypeExtension)(Object)variant1).mavapi$metadata().getId().equals(new Identifier(nbt.getString(VARIANT_KEY)))) {
                this.setVariant(variant1);
                break;
            }
        }
    }
}