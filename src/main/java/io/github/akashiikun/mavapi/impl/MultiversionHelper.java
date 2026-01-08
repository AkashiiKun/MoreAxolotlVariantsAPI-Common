package io.github.akashiikun.mavapi.impl;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class MultiversionHelper {
	public static Identifier toIdentifier(ResourceKey<?> key) {
		//? if >=1.21.11 {
		return key.identifier();
		//?} else {
		//return key.location();
		//?}
	}
}
