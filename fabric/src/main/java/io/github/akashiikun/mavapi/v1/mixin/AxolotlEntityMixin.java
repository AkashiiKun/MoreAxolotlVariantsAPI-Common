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

import com.llamalad7.mixinextras.sugar.Local;
import io.github.akashiikun.mavapi.v1.impl.AxolotlRegistry;
import io.github.akashiikun.mavapi.v1.networking.ServerRespondVariantPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Debug(export = true)
@Mixin(Axolotl.class)
public abstract class AxolotlEntityMixin {

	@Redirect(method = {"addAdditionalSaveData", "method_57305"},
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/nbt/CompoundTag;putInt(Ljava/lang/String;I)V",
					ordinal = 0),
			slice = @Slice(from = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/entity/animal/axolotl/Axolotl;getVariant()Lnet/minecraft/world/entity/animal/axolotl/Axolotl$Variant;"))
	)
	private void mavapi$addAdditionalSaveData(CompoundTag instance, String key, int value) {
		instance.putString(Axolotl.VARIANT_TAG, AxolotlRegistry.getKey(value).toString());
	}

	@Redirect(method = {"readAdditionalSaveData", "loadFromBucketTag"},
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/entity/animal/axolotl/Axolotl$Variant;byId(I)Lnet/minecraft/world/entity/animal/axolotl/Axolotl$Variant;"))
	private Axolotl.Variant loadVariant(int id, @Local(argsOnly = true) CompoundTag nbt) {
        MinecraftServer server = ((Entity)(Object)this).getServer();

        if (server != null) {
            server.getPlayerList().getPlayers().forEach((player) -> {
                ServerPlayNetworking.send(player, new ServerRespondVariantPayload(((Entity)(Object)this).getId(), AxolotlRegistry.loadVariant(nbt)));
            });
        }
		return AxolotlRegistry.loadVariant(id, nbt);
	}

}