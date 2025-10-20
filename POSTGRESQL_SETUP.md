# Настройка PostgreSQL для MessengerMod

## 1. Установка PostgreSQL

1. Скачайте и установите PostgreSQL с официального сайта: https://www.postgresql.org/download/
2. Запомните пароль для пользователя `postgres` (администратор)

## 2. Создание базы данных и пользователя

Откройте командную строку PostgreSQL (psql) и выполните следующие команды:

```sql
-- Подключение к PostgreSQL как администратор
psql -U postgres

-- Создание базы данных
CREATE DATABASE messenger_db;

-- Создание пользователя
CREATE USER messenger_user WITH PASSWORD 'messenger_pass';

-- Предоставление прав пользователю
GRANT ALL PRIVILEGES ON DATABASE messenger_db TO messenger_user;

-- Подключение к базе данных
\c messenger_db

-- Предоставление прав на схему public
GRANT ALL ON SCHEMA public TO messenger_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO messenger_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO messenger_user;

-- Выход
\q
```

## 3. Создание таблицы

Подключитесь к базе данных и создайте таблицу:

```sql
psql -U messenger_user -d messenger_db

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    text VARCHAR(256) NOT NULL
);
```

## 4. Проверка подключения

Мод автоматически подключится к базе данных при запуске сервера. Проверьте логи сервера на наличие сообщений о подключении к БД.

## 5. Настройка подключения

Если нужно изменить настройки подключения, отредактируйте файл `src/main/resources/META-INF/persistence.xml`:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/messenger_db"/>
<property name="jakarta.persistence.jdbc.user" value="messenger_user"/>
<property name="jakarta.persistence.jdbc.password" value="messenger_pass"/>
```

## 6. Проверка работы

1. Запустите сервер Minecraft с модом
2. Подключитесь к серверу
3. Нажмите клавишу `M` для открытия экрана сообщений
4. Введите сообщение и отправьте
5. Проверьте, что сообщение сохранилось в базе данных:

```sql
SELECT * FROM messages;
```