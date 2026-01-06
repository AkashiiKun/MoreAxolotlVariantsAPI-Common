package io.github.akashiikun.mavapi.impl.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.akashiikun.mavapi.api.v2.AxolotlVariant;
import io.github.akashiikun.mavapi.api.v2.AxolotlVariants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.MissingItemModel;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
	@SuppressWarnings("MixinAnnotationTarget") // IDE doesn't like wildcards apparently
	@WrapOperation(method = "*", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"))
	private <T> T get(ItemStack stack, DataComponentType<T> dataComponentType, Operation<T> original) {
		Identifier identifier;
		Holder<AxolotlVariant> axolotlVariantHolder;
		if (dataComponentType == DataComponents.ITEM_MODEL && stack.is(Items.AXOLOTL_BUCKET) && !stack.hasNonDefault(DataComponents.ITEM_MODEL) && AxolotlVariants.hasAxolotlVariantComponent(stack) && (axolotlVariantHolder = AxolotlVariants.getBucketVariant(Minecraft.getInstance().level.registryAccess(), stack).orElse(null)) != null && !(Minecraft.getInstance().getModelManager().getItemModel(identifier = axolotlVariantHolder.unwrapKey().map(a -> a.identifier()).map(a -> a.withSuffix("_axolotl_bucket")).orElse(Identifier.withDefaultNamespace("missingno"))) instanceof MissingItemModel)) {
			//noinspection unchecked
			return (T) identifier;
		}
		return original.call(stack, dataComponentType);
	}
}
