package ru.mephi.chatservice.init

import jakarta.annotation.PostConstruct
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component


@Component
class ChatDatabaseInitializer(private val databaseClient: DatabaseClient) {

    @PostConstruct
    fun initialize() {
        databaseClient.sql(
            """
            CREATE TABLE IF NOT EXISTS  chats (
                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                name VARCHAR(255) NOT NULL
            )
            """
        ).fetch().rowsUpdated().subscribe()
    }
}
