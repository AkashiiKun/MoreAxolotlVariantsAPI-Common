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
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Axolotl.Variant.class)
public class AxolotlTypeMixin implements AxolotlTypeExtension {

    @Unique
    private MoreAxolotlVariant metadata;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void mavapi$init(String string, int i, int id, String name, boolean natural, CallbackInfo ci) {
        metadata = MoreAxolotlVariant.make((Axolotl.Variant) (Object) this);
        AxolotlVariants.BY_ID.put(metadata.getId(), metadata);
    }

    @Override
    public MoreAxolotlVariant mavapi$metadata() {
        return metadata;
    }


    @Inject(method = "getName", at = @At(("HEAD")), cancellable = true)
    public void mavapi$getName(CallbackInfoReturnable<String> cir) {
        if (MoreAxolotlVariant.p && metadata.isModded()) cir.setReturnValue("car");
    }

}