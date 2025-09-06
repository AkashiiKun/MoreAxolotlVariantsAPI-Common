package io.github.akashiikun.mavapi.v1.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ClientRequestVariantPayload(UUID uuid) implements CustomPacketPayload {
    public static final Type<ClientRequestVariantPayload> ID = new Type<>(MAVAPINetworkConstant.CLIENT_REQUEST_VARIANT_ID);
    public static final StreamCodec<FriendlyByteBuf, ClientRequestVariantPayload> PACKET_CODEC = StreamCodec.ofMember(ClientRequestVariantPayload::write, ClientRequestVariantPayload::read);

    private static ClientRequestVariantPayload read(FriendlyByteBuf buf) {
        UUID uuid = buf.readUUID();
        return new ClientRequestVariantPayload(uuid);
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}