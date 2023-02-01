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

package io.github.akashiikun.mavapi.v1.api;

import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.ModdedAxolotlVariantImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

import java.util.Locale;

public class ModdedAxolotlVariant {
    public static Builder register(ResourceLocation id) {
        var builder = new Builder();
        builder.id = id;
        return builder;
    }
    public static class Builder {
        private boolean natural = false;
        private ResourceLocation id;
        private Builder(){}
        public Builder natural() {
            natural = true;
            return this;
        }
        public Axolotl.Variant build() {
            Axolotl.Variant[] variants = Axolotl.Variant.values();
            Axolotl.Variant lastVariant = variants[variants.length-1];
            String internalName = id.toString().toLowerCase(Locale.ROOT);
            int ordinal = variants[variants.length-1].ordinal()+1;
            int id = lastVariant.getId()+1;
            String name = internalName;
            boolean natural = this.natural;
            Axolotl.Variant variant = ModdedAxolotlVariantImpl.create(internalName, ordinal, id, name, natural);
            ((AxolotlTypeExtension) (Object) variant).mavapi$metadata().modded();
            ((AxolotlTypeExtension) (Object) variant).mavapi$metadata().setId(this.id);
            return variant;
        }
    }
}