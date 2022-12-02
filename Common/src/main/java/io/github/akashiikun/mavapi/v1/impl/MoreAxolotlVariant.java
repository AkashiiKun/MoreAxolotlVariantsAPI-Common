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

import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.util.Identifier;

public class MoreAxolotlVariant {
    public static boolean p = false;
    private boolean modded = false;
    private Identifier id;
    private AxolotlEntity.Variant type;
    public static MoreAxolotlVariant make(AxolotlEntity.Variant type) {
        MoreAxolotlVariant moreAxolotlVariant = new MoreAxolotlVariant();
        moreAxolotlVariant.type = type;
        return moreAxolotlVariant;
    }

    public void modded() {
        modded = true;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public boolean isModded() {
        return modded;
    }

    public Identifier getId() {
        return !modded ? new Identifier(type.getName()) : id;
    }
}