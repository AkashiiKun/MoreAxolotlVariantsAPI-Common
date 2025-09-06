package io.github.akashiikun.mavapi.v1.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record ServerRespondVariantPayload(int entityId, String variant) implements CustomPacketPayload {
    public static final Type<ServerRespondVariantPayload> ID = new Type<>(MAVAPINetworkConstant.SERVER_RESPOND_VARIANT_ID);
    public static final StreamCodec<FriendlyByteBuf, ServerRespondVariantPayload> PACKET_CODEC = StreamCodec.ofMember(ServerRespondVariantPayload::write, ServerRespondVariantPayload::read);

    private static ServerRespondVariantPayload read(FriendlyByteBuf buf) {
        return new ServerRespondVariantPayload(
                buf.readInt(),
                buf.readUtf()
        );
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(variant);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}