package ru.minecraft.messenger.network;

import lombok.extern.slf4j.Slf4j;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import org.jetbrains.annotations.NotNull;

/**
 * Payload для отправки Protobuf-сообщения от клиента к серверу.
 * Мы храним данные в виде Protobuf-байтов (byte[] data).
 */
@Slf4j
public record MessagePayload(byte[] data) implements CustomPacketPayload {

    public static final Type<MessagePayload> MESSAGE_TYPE = new Type<>(MessengerNetworking.SEND_MESSAGE_ID);

    public static final StreamCodec<FriendlyByteBuf, MessagePayload> MESSAGE_CODEC = StreamCodec.of(
            (buf, payload) -> {
                buf.writeVarInt(payload.data.length);
                buf.writeBytes(payload.data);
            },
            buf -> {
                final int len = buf.readVarInt();
                final byte[] bytes = new byte[len];
                buf.readBytes(bytes);
                return new MessagePayload(bytes);
            }
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return MESSAGE_TYPE;
    }

    /**
     * Регистрирует пакет в реестре типов Fabric (должен быть вызван в ClientModInitializer).
     */
    public static void register() {
        try {
            PayloadTypeRegistry.playC2S().register(MESSAGE_TYPE, MESSAGE_CODEC);
            log.info("Client: MessagePayload registered with ID: {}", MessengerNetworking.SEND_MESSAGE_ID);
        } catch (Exception e) {
            log.error("Client: Failed to register MessagePayload: {}", e.getMessage(), e);
        }
    }

    /**
     * Регистрирует пакет на сервере (должен быть вызван в ModInitializer).
     */
    public static void registerServer() {
        try {
            PayloadTypeRegistry.playC2S().register(MESSAGE_TYPE, MESSAGE_CODEC);
            log.info("Server: MessagePayload registered on server with ID: {}", MessengerNetworking.SEND_MESSAGE_ID);
        } catch (Exception e) {
            log.error("Server: Failed to register MessagePayload on server: {}", e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof MessagePayload that)) return false;
        return java.util.Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return java.util.Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        return "MessagePayload{" + "data=" + java.util.Arrays.toString(data) + '}';
    }
}
