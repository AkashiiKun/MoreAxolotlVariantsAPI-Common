// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
//? if neoforge {
/*package io.github.akashiikun.mavapi.impl.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.neoforged.neoforge.common.CommonHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Collection;

@Mixin(CommonHooks.class)
public class CommonHooksMixin {
	@ModifyVariable(method = "verifyEntityDataAccessorRegistration", at = @At("STORE"), name = "isValid")
	private static boolean mavapi$verifyEntityDataAccessorRegistration(boolean valid, @Local(name = "mixinsInjectingEda") Collection<String> mixinsInjectingEda) {
		return valid || mixinsInjectingEda.contains("io.github.akashiikun.mavapi.impl.mixin.AxolotlMixin");
	}
}
*///?}
