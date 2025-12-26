package io.github.akashiikun.mavapi.api.v2;

import io.github.akashiikun.mavapi.impl.AxolotlMigrationsImpl;
import net.minecraft.resources.Identifier;

/// Change the name of an axolotl variant from MavApi v1 when migrating to MavApi v2. Does not work with an existing v2 variant! If not specified, axolotl variants keep their v1 name.
public class V1AxolotlMigrations {
	public static void migrateToV2(Identifier oldName, Identifier newName) {
		AxolotlMigrationsImpl.namesToMigrate.put(oldName, newName);
	}
}
