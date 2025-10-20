package ru.minecraft.messenger.entity;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Сущность JPA для маппинга на таблицу 'messages' в PostgreSQL.
 * Использует Lombok для автоматической генерации геттеров, сеттеров и конструкторов.
 */
@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "text", nullable = false, length = 256)
    private String text;

    /**
    Пользовательский конструктор для удобного создания сущности перед сохранением
     */
    public MessageEntity(final UUID uuid, final String text) {
        this.uuid = uuid;
        this.text = text;
    }
}
