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