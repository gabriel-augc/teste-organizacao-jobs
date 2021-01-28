package com.testeVivo.constantes;

public class kafkaConstantes {
    public static String BOOTSTRAP_SERVERS_CONFIG = System.getenv().getOrDefault(
            "BOOTSTRAP_SERVERS_CONFIG","localhost:29092");

    public static String TOPIC_NAME = System.getenv().getOrDefault(
            "BOOTSTRAP_SERVERS_CONFIG","jobs");

    public static String GROUP_ID_CONFIG = System.getenv().getOrDefault(
            "BOOTSTRAP_SERVERS_CONFIG","consumerGroup10");

    public static Integer MAX_NO_MESSAGE_FOUND_COUNT = Integer.parseInt(
            System.getenv().getOrDefault("BOOTSTRAP_SERVERS_CONFIG", "100"));

    public static String AUTO_OFFSET_RESET_CONFIG = System.getenv().getOrDefault(
            "BOOTSTRAP_SERVERS_CONFIG","earliest");

    public static Integer MAX_POLL_RECORDS_CONFIG = Integer.parseInt(
            System.getenv().getOrDefault("BOOTSTRAP_SERVERS_CONFIG", "1"));
}
