package ru.minecraft.messenger.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import ru.minecraft.messenger.entity.MessageEntity;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с сообщениями в БД.
 * Использует JPA (Hibernate) для сохранения и выборки данных.
 */
@Slf4j
public class MessageRepository {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private MessageRepository() {}

    static {
        try {
            emf = Persistence.createEntityManagerFactory("messenger-pu");
            em = emf.createEntityManager();
            log.info("Hibernate: EntityManager initialized");
        } catch (Exception e) {
            log.error("Hibernate init failed: {}", e.getMessage(), e);
        }
    }

    /** Сохраняет сообщение игрока в БД. */
    public static void saveMessage(final UUID playerUuid, final String text) {
        try {
            em.getTransaction().begin();
            em.persist(new MessageEntity(playerUuid, text));
            em.getTransaction().commit();
            log.info("Message saved to DB for UUID: {}", playerUuid);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            log.error("Failed to save message for UUID {}: {}", playerUuid, e.getMessage(), e);
        }
    }

    /** Закрывает EntityManager и фабрику. */
    public static void close() {
        if (em != null) em.close();
        if (emf != null) emf.close();
        log.info("Hibernate: EntityManager closed");
    }
}
