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
