package io.github.akashiikun.mavapi.fabric;

import io.github.akashiikun.mavapi.v1.networking.ClientRequestVariantPayload;
import io.github.akashiikun.mavapi.v1.networking.ServerRespondVariantPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class MoreAxolotlVariantsModAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            // Check if entity can have variants so we don't make useless requests
            if (entity.getType().equals(EntityType.AXOLOTL)) {
                ClientPlayNetworking.send(new ClientRequestVariantPayload(entity.getUUID()));
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(ServerRespondVariantPayload.ID, ((payload, context) -> {
            var client = context.client();
            int id = payload.entityId();
            String variantId = payload.variant();
            client.execute(() -> {
                if (client.level != null) {
                    Entity entity = client.level.getEntity(id);
                    if (entity != null) {
                        CompoundTag nbt = new CompoundTag();
                        entity.saveWithoutId(nbt);
                        nbt.putString(MoreAxolotlVariantAPIMod.NBT_KEY, variantId);
                        entity.load(nbt);
                    }
                }
            });
        }));
    }
}
