// Copyright (c) 2025 Jab125. All rights reserved.
// This file is part of More Axolotl Variants API.
// More Axolotl Variants API is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
// More Axolotl Variants API distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// You should have received a copy of the GNU Lesser General Public License along with More Axolotl Variants API. If not, see <https://www.gnu.org/licenses/>.
package io.github.akashiikun.mavapi.impl.mixin;

import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;
import io.github.akashiikun.mavapi.api.v2.MavApiDataComponents;
import io.github.akashiikun.mavapi.impl.VariantTooltipProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {
	@Shadow
	@Final
	private PatchedDataComponentMap components;

	@Inject(method = "addDetailsToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", shift = At.Shift.AFTER))
	void mavapi$addDetailsToTooltip(Item.TooltipContext context, TooltipDisplay tooltipDisplay, Player player, TooltipFlag tooltipFlag, Consumer<Component> tooltipAdder, CallbackInfo ci) {
		this.mavapi$addToTooltip(MavApiDataComponents.AXOLOTL_VARIANT, context, tooltipDisplay, tooltipAdder, tooltipFlag);
		this.mavapi$addToTooltip2(DataComponents.AXOLOTL_VARIANT, context, tooltipDisplay, tooltipAdder, tooltipFlag);
	}

	@Unique
	private void mavapi$addToTooltip(
			DataComponentType<Holder<AxolotlVariant>> component, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag
	) {
		Holder<AxolotlVariant> tooltipProvider = this.get(component);
		if (tooltipProvider != null && tooltipDisplay.shows(component)) {
			new VariantTooltipProvider(tooltipProvider).addToTooltip(context, tooltipAdder, tooltipFlag, this.components);
		}
	}

	@Unique
	private void mavapi$addToTooltip2(
			DataComponentType<Axolotl.Variant> component, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag
	) {
		Axolotl.Variant tooltipProvider = this.get(component);
		if (tooltipProvider != null && tooltipDisplay.shows(component)) {
			new VariantTooltipProvider(context.registries(), tooltipProvider).addToTooltip(context, tooltipAdder, tooltipFlag, this.components);
		}
	}
}
