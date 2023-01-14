/*
 * Copyright (c) 2021 - 2023 Jab125, LimeAppleBoat & 2022 - 2023 Akashii
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

package io.github.akashiikun.mavapi.v1.impl.mixin;

import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Locale;
import java.util.Map;
import net.minecraft.client.renderer.entity.AxolotlRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

@Mixin(AxolotlRenderer.class)
public class AxolotlEntityRendererMixin {
    @Shadow
    @Mutable
    @Final
    private static Map<Axolotl.Variant, ResourceLocation> TEXTURE_BY_TYPE;

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void mavapi$ModifyVariantTextures(CallbackInfo ci) {
            for(Axolotl.Variant variant : Axolotl.Variant.values()) {
                MoreAxolotlVariant metadata = ((AxolotlTypeExtension)(Object)variant).mavapi$metadata();
                if(metadata.isModded()) {
                    TEXTURE_BY_TYPE.replace(variant, new ResourceLocation(metadata.getId().getNamespace(), String.format(Locale.ROOT, "textures/entity/axolotl/axolotl_%s.png", metadata.getId().getPath())));
                }
            }
    }

    @Inject(method = "<clinit>", at = @At("HEAD"))
    private static void mavapi$clinit(CallbackInfo ci) {
        MoreAxolotlVariant.p = true;
    }
    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void mavapi$clinit2(CallbackInfo ci) {
        MoreAxolotlVariant.p = false;
    }
}