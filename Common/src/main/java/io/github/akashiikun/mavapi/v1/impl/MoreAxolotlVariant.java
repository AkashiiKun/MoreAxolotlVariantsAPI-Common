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