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
