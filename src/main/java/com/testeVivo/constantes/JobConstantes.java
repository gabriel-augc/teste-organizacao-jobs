package com.testeVivo.constantes;

public class JobConstantes {
    public static String INICIO_JANELA = System.getenv().getOrDefault("INICIO_JANELA", "2021-01-28");

    public static String FIM_JANELA = System.getenv().getOrDefault("FIM_JANELA", "2021-02-28");

    public static int HORA_INICIO_EXECUCAO = Integer.parseInt(System.getenv()
            .getOrDefault("HORA_INICIO_EXECUCAO", "8"));

    public static int HORA_FIM_EXECUCAO = Integer.parseInt(System.getenv()
            .getOrDefault("HORA_FIM_EXECUCAO", "16"));
}
