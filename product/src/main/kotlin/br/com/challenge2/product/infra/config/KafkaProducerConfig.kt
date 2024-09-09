package br.com.challenge2.product.infra.config

import br.com.challenge2.product.avro.ProductAvro
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig {
    @Value("\${spring.kafka.bootstrap-servers:localhost:9092}")
    private val bootstrapAddress: String? = null

    @Bean
    fun producerFactory(): ProducerFactory<String, List<ProductAvro>> {
        val props = HashMap<String, Any?>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props["schema.registry.url"] = "http://localhost:8081"
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java

        return DefaultKafkaProducerFactory<String, List<ProductAvro>>(props)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, List<ProductAvro>> {
        return KafkaTemplate<String, List<ProductAvro>>(producerFactory())
    }
}