package ru.minecraft.messenger;


import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.ModInitializer;
import ru.minecraft.messenger.network.MessagePayload;
import ru.minecraft.messenger.network.ServerPacketHandler;

/**
 * Точка входа мода на стороне сервера.
 * Инициализирует сетевые сообщения и обработчики пакетов.
 */
@Slf4j
public class MessengerMod implements ModInitializer {

    @Override
    public void onInitialize() {
        log.info("MessengerMod: Initializing server side...");

        MessagePayload.registerServer();
        log.info("MessengerMod: MessagePayload registered on server");

        ServerPacketHandler.register();
        log.info("MessengerMod: ServerPacketHandler registered");
    }
}
