package ru.minecraft.messenger;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import ru.minecraft.messenger.screen.MessageScreen;
import ru.minecraft.messenger.network.MessagePayload;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * Клиентская часть мода.
 * Регистрирует сетевые payload'ы и клавишу открытия окна сообщений.
 */
@Slf4j
public class MessengerModClient implements ClientModInitializer {

	public static final KeyMapping OPEN_MESSENGER_KEY = KeyBindingHelper.registerKeyBinding(new KeyMapping(
			"key.messenger.open",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_M,
			"category.messenger"
	));

	@Override
	public void onInitializeClient() {
		log.info("Client Mod Initializer: Initializing...");

		MessagePayload.register();
		log.info("Client Mod Initializer: All networking payloads registered.");

		ClientTickEvents.END_CLIENT_TICK.register(client -> {

			if (OPEN_MESSENGER_KEY.consumeClick()
					&& client.player != null
					&& client.screen == null) {

				client.execute(() -> client.setScreen(new MessageScreen()));

				log.debug("Key press detected. Opening MessageScreen.");
			}
		});
	}
}
