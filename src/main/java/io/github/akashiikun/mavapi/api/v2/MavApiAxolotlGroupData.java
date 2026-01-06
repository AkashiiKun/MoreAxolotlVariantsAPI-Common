// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.api.v2;

import io.github.akashiikun.mavapi.impl.extension.AxolotlGroupDataExtension;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class MavApiAxolotlGroupData {
	@SafeVarargs
	public static Axolotl.AxolotlGroupData create(Holder<AxolotlVariant>... variants) {
		Axolotl.AxolotlGroupData axolotlGroupData = new Axolotl.AxolotlGroupData(null);
		((AxolotlGroupDataExtension) axolotlGroupData).setVariants(variants);
		return axolotlGroupData;
	}

	public static Holder<AxolotlVariant> getVariant(Axolotl.AxolotlGroupData axolotlGroupData, RandomSource randomSource) {
		return ((AxolotlGroupDataExtension) axolotlGroupData).getVariant(randomSource);
	}
}
