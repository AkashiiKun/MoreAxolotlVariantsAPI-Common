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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class MoreAxolotlVariant {

    public static boolean p = false;

    private boolean modded = false;
    private int legacyIndex = -1;
    private ResourceLocation id;
    private Axolotl.Variant type;

    public static MoreAxolotlVariant make(Axolotl.Variant type) {
        MoreAxolotlVariant moreAxolotlVariant = new MoreAxolotlVariant();
        moreAxolotlVariant.type = type;
        moreAxolotlVariant.setLegacyIndex(type.getId());
        return moreAxolotlVariant;
    }

    public void modded() {
        modded = true;
    }

    public void setLegacyIndex(int legacyIndex) {
        this.legacyIndex = legacyIndex;
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public boolean isModded() {
        return modded;
    }

    public int getLegacyIndex() {
        return legacyIndex;
    }

    public ResourceLocation getId() {
        return !modded ? new ResourceLocation(type.getName()) : id;
    }

    public Axolotl.Variant getType() {
        return type;
    }

}