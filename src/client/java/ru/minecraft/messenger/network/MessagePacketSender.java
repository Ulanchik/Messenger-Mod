package ru.minecraft.messenger.network;


import lombok.extern.slf4j.Slf4j;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import ru.minecraft.messenger.proto.MessageProtos;

/**
 * Отправка сообщений с клиента на сервер.
 * Формирует Protobuf-сообщение и отправляет его через канал {@link MessagePayload}.
 */
@Slf4j
public class MessagePacketSender {

    private MessagePacketSender() {
    }

    /**
     * Отправляет текстовое сообщение на сервер.
     *
     * @param messageContent текст сообщения
     */
    public static void sendToServer(final String messageContent) {
        log.info("Attempting to send message: '{}'", messageContent);
        log.debug("Channel ID: {}", MessengerNetworking.SEND_MESSAGE_ID);

        final boolean canSend = ClientPlayNetworking.canSend(MessengerNetworking.SEND_MESSAGE_ID);
        log.debug("Can send packet: {}", canSend);

        if (canSend) {

            final MessageProtos.Message message = MessageProtos.Message.newBuilder()
                                                                       .setText(messageContent)
                                                                       .build();

            final byte[] bytes = message.toByteArray();
            log.debug("Serialized message to {} bytes.", bytes.length);

            ClientPlayNetworking.send(new MessagePayload(bytes));

            log.info("Message sent successfully: '{}'", messageContent);

        } else {
            log.error("Cannot send message packet. Channel '{}' not registered or client is not connected to the server.",
                      MessengerNetworking.SEND_MESSAGE_ID);
        }
    }
}
