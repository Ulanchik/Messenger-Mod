package ru.minecraft.messenger.screen;


import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.minecraft.messenger.network.MessagePacketSender;

/**
 * Экран мессенджера в игре.
 * Позволяет ввести текст и отправить его на сервер.
 * Использует стандартные виджеты Minecraft (EditBox, Button).
 */
public class MessageScreen extends Screen {

    private EditBox textField;
    private Button sendButton;

    /**
     * Создает экран мессенджера с заголовком "Messenger".
     */
    public MessageScreen() {
        super(Component.literal("Messenger"));
    }

    @Override
    protected void init() {
        super.init();

        textField = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 20, 200, 20, Component.literal(""));
        textField.setMaxLength(256);
        textField.setSuggestion(String.valueOf(Component.literal("Введите сообщение...")));
        this.addRenderableWidget(textField);

        sendButton = Button.builder(Component.literal("Отправить"), btn -> {
            final String message = textField.getValue().trim();
            if (!message.isEmpty()) {
                MessagePacketSender.sendToServer(message);

                textField.setValue("");

                this.onClose();
            }
        }).size(120, 20)
        .pos(this.width / 2 - 60, this.height / 2 + 10)
        .build();

        this.addRenderableWidget(sendButton);

        final Button cancelButton = Button.builder(Component.literal("Отмена"), btn -> this.onClose()).size(120, 20)
                                          .pos(this.width / 2 - 60, this.height / 2 + 40)
                                          .build();

        this.addRenderableWidget(cancelButton);
    }


    @Override
    public void render(final GuiGraphics graphics, final int mouseX, final int mouseY, final float delta) {
        super.render(graphics, mouseX, mouseY, delta);

        graphics.drawCenteredString(
                this.font,
                this.title,
                this.width / 2,
                this.height / 2 - 60,
                0xFFFFFF
        );
    }

    @Override
    public boolean keyPressed(final int keyCode, final int scanCode, final int modifiers) {
        // Проверяем Enter и фокус на текстовом поле или кнопке отправки
        if (keyCode == 257 && (sendButton.isFocused() || textField.isFocused())) {
            final String message = textField.getValue().trim();
            if (!message.isEmpty()) {
                MessagePacketSender.sendToServer(message);
                textField.setValue("");
                this.onClose();
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}

