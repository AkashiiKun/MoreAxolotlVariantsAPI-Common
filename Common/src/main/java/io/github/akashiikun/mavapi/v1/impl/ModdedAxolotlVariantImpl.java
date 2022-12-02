/*
 * Copyright (c) 2021 - 2022 Jab125, LimeAppleBoat & 2022 - 2022 Akashii
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

import io.github.akashiikun.mavapi.v1.impl.mixin.VariantWidener;
import net.minecraft.entity.passive.AxolotlEntity;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ModdedAxolotlVariantImpl {
    @ApiStatus.Internal
    public static AxolotlEntity.Variant create(String internalName, int ordinal, int id, String name, boolean natural) {
        var d = new ArrayList<>(Arrays.stream(AxolotlEntity.Variant.VARIANTS).toList());
        var e = VariantWidener.newVariant(internalName, ordinal, id, name, natural);
        d.add(e);
        VariantWidener.setVARIANTS(d.stream().sorted(Comparator.comparingInt(AxolotlEntity.Variant::getId)).toArray(AxolotlEntity.Variant[]::new));
        return e;
    }
}