// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.impl;

import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class AxolotlMigrationsImpl {
	public static final Map<Identifier, Identifier> namesToMigrate = new HashMap<>();

	public static String migrateName(String oldName) {
		Identifier oldNameAsAnIdentifier = Identifier.parse(oldName);
		return namesToMigrate.getOrDefault(oldNameAsAnIdentifier, oldNameAsAnIdentifier).toString();
	}
}
