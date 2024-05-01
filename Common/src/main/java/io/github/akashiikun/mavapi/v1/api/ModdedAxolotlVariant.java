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

package io.github.akashiikun.mavapi.v1.api;

import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.AxolotlVariantAPI;
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
        private int legacyIndex = -1;
        private ResourceLocation id;

        private Builder() {

        }

        public Builder natural() {
            natural = true;
            return this;
        }

        public Builder setLegacyIndex(int legacyIndex) {
            this.legacyIndex = legacyIndex;
            return this;
        }

        public Axolotl.Variant build() {
            Axolotl.Variant[] variants = Axolotl.Variant.values();
            Axolotl.Variant lastVariant = variants[variants.length-1];
            String internalName = id.toString().toLowerCase(Locale.ENGLISH);
            int ordinal = variants[variants.length-1].ordinal()+1;
            int id = lastVariant.getId()+1;
            String name = internalName;
            boolean natural = this.natural;
            Axolotl.Variant variant = AxolotlVariantAPI.create(internalName, ordinal, id, name, natural);
            ((AxolotlTypeExtension) (Object) variant).mavapi$metadata().modded();
            ((AxolotlTypeExtension) (Object) variant).mavapi$metadata().setId(this.id);
            ((AxolotlTypeExtension) (Object) variant).mavapi$metadata().setLegacyIndex(this.legacyIndex);
            return variant;
        }

    }

}