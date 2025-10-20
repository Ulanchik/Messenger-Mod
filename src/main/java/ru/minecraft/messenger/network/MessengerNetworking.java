package ru.minecraft.messenger.network;

import net.minecraft.resources.ResourceLocation;

/**
 * Центральный класс для хранения ResourceLocation'ов
 * сетевых каналов мода.
 */
public class MessengerNetworking {

    private MessengerNetworking() {
    }

    public static final ResourceLocation SEND_MESSAGE_ID =
            ResourceLocation.fromNamespaceAndPath("messengermod", "send_message");

}
