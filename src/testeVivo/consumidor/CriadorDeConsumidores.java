package testeVivo.consumidor;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import testeVivo.constantes.IkafkaConstantes;

import java.util.Collections;
import java.util.Properties;

public class CriadorDeConsumidores {
    public static Consumer<Long, String> criar() {
        final Properties propriedades = new Properties();
        propriedades.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IkafkaConstantes.BOOTSTRAP_SERVERS_CONFIG);
        propriedades.put(ConsumerConfig.GROUP_ID_CONFIG, IkafkaConstantes.GROUP_ID_CONFIG);
        propriedades.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        propriedades.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        propriedades.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, IkafkaConstantes.MAX_POLL_RECORDS_CONFIG);
        propriedades.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        propriedades.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, IkafkaConstantes.AUTO_OFFSET_RESET_CONFIG);

        final Consumer<Long, String> consumidor = new KafkaConsumer<Long, String>(propriedades);
        consumidor.subscribe(Collections.singletonList(IkafkaConstantes.TOPIC_NAME));
        return consumidor;
    }
}
