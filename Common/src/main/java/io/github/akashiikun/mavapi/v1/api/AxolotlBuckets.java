package io.github.akashiikun.mavapi.v1.api;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.util.List;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class AxolotlBuckets {

	public static boolean doesModelForBucketExist(ResourceLocation resourceLocation) {
		ResourceLocation fileLocation = new ResourceLocation(resourceLocation.getNamespace(), String.format("models/item/axolotl_bucket_%s.json", resourceLocation.getPath()));
		List<Resource> stack = Minecraft.getInstance().getResourceManager().getResourceStack(fileLocation);
		return stack.size() > 0; // Model exists
	}

}
