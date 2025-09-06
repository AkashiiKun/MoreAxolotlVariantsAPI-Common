package io.github.akashiikun.mavapi.fabric;

import io.github.akashiikun.mavapi.v1.networking.ClientRequestVariantPayload;
import io.github.akashiikun.mavapi.v1.networking.ServerRespondVariantPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class MoreAxolotlVariantAPIMod implements ModInitializer {

    public static final String NBT_KEY = "Variant";

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(ClientRequestVariantPayload.ID, ClientRequestVariantPayload.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(ServerRespondVariantPayload.ID, ServerRespondVariantPayload.PACKET_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ClientRequestVariantPayload.ID, ((payload, context) -> {
            var server = context.server();
            var uuid = payload.uuid();

            server.execute( () -> {
                Entity entity = server.overworld().getEntity(uuid);

                if (entity == null) {
                    for (ServerLevel serverWorld : server.getAllLevels()) {
                        Entity entity2 = serverWorld.getEntity(uuid);
                        if (entity2 != null) {
                            entity = entity2;
                        }
                    }
                }

                if (entity != null) {
                    CompoundTag nbt = new CompoundTag();
                    entity.saveWithoutId(nbt);

                    if (nbt.contains(NBT_KEY)) {
                        //going to pass all three of these regardless, so buf structure is constant. More cases can be added and hook into these as needed.
                        boolean bl = false;
                        int i = 0;
                        String str = "";

                        var responsePayload = new ServerRespondVariantPayload(entity.getId(), nbt.getString(NBT_KEY));
                        ServerPlayNetworking.send(context.player(), responsePayload);
                    }
                }
            });

        }));
    }

}
