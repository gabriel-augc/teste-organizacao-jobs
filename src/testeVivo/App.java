package testeVivo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import testeVivo.constantes.IkafkaConstantes;
import testeVivo.consumidor.CriadorDeConsumidores;

public class App {
    public static void main(String[] args) {
        run();
    }

    static void run() {
        Consumer<Long, String> consumidor = CriadorDeConsumidores.criar();

        int mensagensNaoEncontradas = 0;

        while (true) {
            final ConsumerRecords<Long, String> registros = consumidor.poll(1000);
            if (registros.count() == 0) {
                mensagensNaoEncontradas++;
                if (mensagensNaoEncontradas > IkafkaConstantes.MAX_NO_MESSAGE_FOUND_COUNT)
                    break;
                else
                    continue;
            }

            registros.forEach(registro -> {
                System.out.println(registro.value());
            });
            consumidor.commitAsync();
        }
        consumidor.close();
    }
}