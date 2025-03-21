/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 - 2024 Akashii, 2023 - 2024 KxmischesDomi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.akashiikun.mavapi.v1.mixin;

import io.github.akashiikun.mavapi.v1.impl.AxolotlRegistry;
import io.github.akashiikun.mavapi.v1.impl.AxolotlTypeExtension;
import io.github.akashiikun.mavapi.v1.impl.Pivot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.IntFunction;

@Debug(export = true)
@Mixin(Axolotl.Variant.class)
public class AxolotlTypeMixin implements AxolotlTypeExtension {
	@Shadow
	@Final
	private int id;

	@Shadow
	@Final
	@Mutable
	private static IntFunction<Axolotl.Variant> BY_ID;

	@Unique
	private ResourceLocation identifier;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void mavapi$init(String string, int i, int id, String name, boolean natural, CallbackInfo ci) {
		this.identifier = ResourceLocation.tryParse(name);
		Pivot.INSTANCE.register(AxolotlRegistry.AXOLOTL_VARIANTS_KEY, identifier, (Axolotl.Variant) (Object) this);
	}

	@Override
	public int mavapi$getLegacyId() {
		return this.id;
	}

	@Override
	public ResourceLocation mavapi$getId() {
		return this.identifier;
	}

	/**
	 * @author Ampflower
	 * @reason Change ID to use registry.
	 */
	@Overwrite
	public int getId() {
		if (AxolotlRegistry.getKey((Axolotl.Variant) (Object) this) == null) {
			return this.mavapi$getLegacyId();
		}

		return AxolotlRegistry.AXOLOTL_VARIANTS.getId((Axolotl.Variant) (Object) this);
	}

	/**
	 * @author Ampflower
	 * @reason Change ID to use registry.
	 */
	@Overwrite
	public static Axolotl.Variant byId(int id) {
		return AxolotlRegistry.getValue(id);
	}

	@Redirect(method = "<clinit>",
			at = @At(value = "FIELD",
					target = "Lnet/minecraft/world/entity/animal/axolotl/Axolotl$Variant;BY_ID:Ljava/util/function/IntFunction;",
					opcode = Opcodes.PUTSTATIC))
	private static void fixByIdFunction(IntFunction<Axolotl.Variant> ignored) {
		BY_ID = AxolotlTypeMixin::byId;
	}
}