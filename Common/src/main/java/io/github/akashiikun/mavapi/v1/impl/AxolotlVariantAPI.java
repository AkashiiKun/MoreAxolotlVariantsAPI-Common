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

package io.github.akashiikun.mavapi.v1.impl;

import io.github.akashiikun.mavapi.v1.mixin.VariantWidener;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class AxolotlVariantAPI {
    @ApiStatus.Internal
    public static Axolotl.Variant create(String internalName, int ordinal, int id, String name, boolean natural) {
        var d = new ArrayList<>(Arrays.stream(Axolotl.Variant.values()).toList());
        var e = VariantWidener.newVariant(internalName, ordinal, id, name, natural);
        d.add(e);
        Axolotl.Variant[] variants = d.stream().sorted(Comparator.comparingInt(Axolotl.Variant::getId)).toArray(Axolotl.Variant[]::new);
        VariantWidener.setVariants(variants.clone());
        VariantWidener.setById(variants.clone());
        return e;
    }
}