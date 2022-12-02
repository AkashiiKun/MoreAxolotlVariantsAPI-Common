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

package io.github.akashiikun.mavapi.v1.impl.mixin;

import net.minecraft.entity.passive.AxolotlEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AxolotlEntity.Variant.class)
public interface VariantWidener {
    @Mutable
    @Accessor
    static void setVARIANTS(AxolotlEntity.Variant[] VARIANTS) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    static AxolotlEntity.Variant newVariant(String internalName, int internalId, int id, String name, boolean natural) {
        throw new AssertionError();
    }
}