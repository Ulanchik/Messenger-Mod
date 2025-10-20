package ru.minecraft.messenger.network;

import lombok.extern.slf4j.Slf4j;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import ru.minecraft.messenger.proto.MessageProtos;
import ru.minecraft.messenger.repository.MessageRepository;

/**
 * Обработчик входящих сообщений на сервере.
 * Регистрирует получатель {@link MessagePayload} и сохраняет сообщения в БД.
 */
@Slf4j
public class ServerPacketHandler {

    private ServerPacketHandler() {
    }

    /** Регистрирует глобальный обработчик сетевых сообщений. */
    public static void register() {
        log.info("ServerPacketHandler: Registering receiver for {}", MessagePayload.MESSAGE_TYPE);

        ServerPlayNetworking.registerGlobalReceiver(MessagePayload.MESSAGE_TYPE, (payload, context) -> {

            final ServerPlayer player = context.player();
            final java.util.UUID playerUuid = player.getUUID();

            context.server().execute(() -> {
                try {
                    final MessageProtos.Message message = MessageProtos.Message.parseFrom(payload.data());
                    final String content = message.getText();

                    log.info("INCOMING MESSAGE | Sender UUID: {} | Content: '{}'", playerUuid, content);

                    MessageRepository.saveMessage(playerUuid, content);
                    log.debug("Message successfully persisted for UUID: {}", playerUuid);

                } catch (Exception e) {
                    log.error("Failed to process message from UUID {}. Error: {}", playerUuid, e.getMessage(), e);
                }
            });
        });
    }
}
