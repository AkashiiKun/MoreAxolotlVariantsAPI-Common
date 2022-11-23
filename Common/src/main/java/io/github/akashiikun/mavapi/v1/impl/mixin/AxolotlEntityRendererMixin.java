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
import io.github.akashiikun.mavapi.v1.impl.MoreAxolotlVariant;
import net.minecraft.client.render.entity.AxolotlEntityRenderer;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.util.Identifier;
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

@Mixin(AxolotlEntityRenderer.class)
public class AxolotlEntityRendererMixin {
    @Shadow
    @Mutable
    @Final
    private static Map<AxolotlEntity.Variant, Identifier> TEXTURES;

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void mavapi$ModifyVariantTextures(CallbackInfo ci) {
            for(AxolotlEntity.Variant variant : AxolotlEntity.Variant.VARIANTS) {
                MoreAxolotlVariant metadata = ((AxolotlTypeExtension)(Object)variant).mavapi$metadata();
                if(metadata.isModded()) {
                    TEXTURES.replace(variant, new Identifier(metadata.getId().getNamespace(), String.format(Locale.ROOT, "textures/entity/axolotl/axolotl_%s.png", metadata.getId().getPath())));
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