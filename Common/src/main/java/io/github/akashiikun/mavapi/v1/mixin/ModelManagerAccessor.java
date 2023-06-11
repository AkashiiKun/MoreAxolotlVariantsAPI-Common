package io.github.akashiikun.mavapi.v1.mixin;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Mixin(ModelManager.class)
public interface ModelManagerAccessor {

	@Accessor("bakedRegistry")
	Map<ResourceLocation, BakedModel> getBakedRegistry();

}
