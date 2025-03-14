package ru.mephi.websocket.kafka.producer

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import ru.mephi.websocket.model.dto.kafka.send.UserStatusChangeEvent

@Configuration
class KafkaActivityMessageProducerConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    fun producerConfig(): Map<String, Any> {
        return hashMapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers
        )
    }

    @Bean
    fun activityMessageProducerFactory(): ProducerFactory<String, UserStatusChangeEvent> {
        return DefaultKafkaProducerFactory(
            producerConfig(),
            StringSerializer(),
            JsonSerializer() // Сериализатор для Person
        )
    }

    @Bean
    fun activityMessageKafkaTemplate(
        producerFactory: ProducerFactory<String, UserStatusChangeEvent>
    ): KafkaTemplate<String, UserStatusChangeEvent> {
        return KafkaTemplate(activityMessageProducerFactory())
    }
}