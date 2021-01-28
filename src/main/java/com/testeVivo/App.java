package com.testeVivo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.testeVivo.classificador.ClassificadorDeJobs;
import com.testeVivo.constantes.kafkaConstantes;
import com.testeVivo.consumidor.CriadorDeConsumidores;

public class App {
    public static void main(String[] args) {
        run();
    }

    static void run() {
        Consumer<Long, String> consumidor = CriadorDeConsumidores.criar();

        ClassificadorDeJobs classificador = new ClassificadorDeJobs();
        int mensagensNaoEncontradas = 0;

        while (true) {
            final ConsumerRecords<Long, String> registros = consumidor.poll(1000);
            if (registros.count() == 0) {
                mensagensNaoEncontradas++;
                if (mensagensNaoEncontradas > kafkaConstantes.MAX_NO_MESSAGE_FOUND_COUNT)
                    break;
                else
                    continue;
            }

            registros.forEach(registro -> {
                System.out.println(registro.value());
                classificador.classificar(registro.value());
            });
            consumidor.commitAsync();
        }
        consumidor.close();
    }
}