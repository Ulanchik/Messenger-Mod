# MessengerMod

Fabric мод для Minecraft 1.21.8 с системой обмена сообщениями через Protobuf и сохранением в PostgreSQL.

## Возможности

- ✅ GUI для отправки сообщений (клавиша M)
- ✅ Protobuf 3 для сериализации сообщений
- ✅ Сетевая отправка сообщений клиент-сервер
- ✅ Сохранение в PostgreSQL через Hibernate JPA
- ✅ JPA Repository для работы с БД

## Требования

- Java 21
- Gradle 8.14
- PostgreSQL 12+
- Minecraft 1.21.8
- Fabric Loader 0.17.3+

## Установка и настройка

### 1. Настройка PostgreSQL

Следуйте инструкциям в файле [POSTGRESQL_SETUP.md](POSTGRESQL_SETUP.md)

### 2. Сборка проекта

```bash
# Сборка проекта
.\gradlew.bat build

# Или используйте batch файл
.\build.bat
```

### 3. Установка мода

1. Скопируйте собранный .jar файл в папку `mods` вашего Minecraft сервера
2. Запустите сервер
3. Подключитесь к серверу
4. Нажмите клавишу `M` для открытия GUI

## Использование

1. В игре нажмите клавишу `M`
2. Введите сообщение в текстовое поле
3. Нажмите "Отправить" или Enter
4. Сообщение будет отправлено на сервер через Protobuf
5. Сервер сохранит сообщение в PostgreSQL

## Структура проекта

```
src/
├── main/
│   ├── java/ru/minecraft/messenger/
│   │   ├── MessengerMod.java              # Основной класс мода
│   │   ├── config/
│   │   │   ├── DatabaseConfig.java        # Конфигурация Hibernate
│   │   │   └── SpringConfig.java          # Конфигурация Spring
│   │   ├── entity/
│   │   │   └── MessageEntity.java         # JPA Entity
│   │   ├── network/
│   │   │   └── MessagePacket.java         # Сетевые пакеты
│   │   ├── repository/
│   │   │   └── MessageRepository.java     # JPA Repository
│   │   └── service/
│   │       └── MessageService.java        # Сервис для работы с БД
│   ├── proto/
│   │   └── message.proto                  # Protobuf схема
│   └── resources/
│       └── sql/
│           └── create_table.sql           # SQL скрипт
└── client/
    └── java/ru/minecraft/messenger/
        ├── MessengerModClient.java        # Клиентская инициализация
        └── screen/
            └── MessageScreen.java         # GUI экран
```

## Технические детали

### Protobuf схема

```protobuf
message Message {
    string text = 1;
}
```

### Структура БД

```sql
CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    text VARCHAR(256) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Сетевые пакеты

- **ID пакета**: `messengermod:message`
- **Направление**: Клиент → Сервер
- **Формат**: Protobuf Message в PacketByteBuf

## Разработка

### Добавление новых функций

1. Обновите Protobuf схему в `message.proto`
2. Пересоберите проект для генерации новых классов
3. Обновите сетевые пакеты в `MessagePacket.java`
4. При необходимости обновите Entity и Repository

### Отладка

Логи мода можно найти в:
- Сервер: `logs/latest.log`
- Клиент: `logs/latest.log`

## Лицензия

CC0-1.0 (Creative Commons)

## Автор

MessengerMod Team
