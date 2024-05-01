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

import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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