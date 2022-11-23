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

package io.github.akashiikun.mavapi.v1.api;

import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.ModdedAxolotlVariantImpl;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.util.Identifier;

public class ModdedAxolotlVariant {
    public static Builder register(Identifier id) {
        var builder = new Builder();
        builder.id = id;
        return builder;
    }
    public static class Builder {
        private boolean natural = false;
        private Identifier id;
        private Builder(){}
        public Builder natural() {
            natural = true;
            return this;
        }
        public AxolotlEntity.Variant build() {
            AxolotlEntity.Variant[] variants = AxolotlEntity.Variant.values();
            AxolotlEntity.Variant lastVariant = variants[variants.length-1];
            String internalName = id.toString();
            int ordinal = variants[variants.length-1].ordinal()+1;
            int id = lastVariant.getId()+1;
            String name = internalName;
            boolean natural = this.natural;
            AxolotlEntity.Variant variant = ModdedAxolotlVariantImpl.create(internalName, ordinal, id, name, natural);
            ((AxolotlTypeExtension) (Object) variant).mavapi$metadata().modded();
            ((AxolotlTypeExtension) (Object) variant).mavapi$metadata().setId(this.id);
            return variant;
        }
    }
}