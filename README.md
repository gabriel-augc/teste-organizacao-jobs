# Teste Vivo

**Índice**
1. [Introdução](#intr)
2. [Configuração de ambiente](#cs1)
3. [Executar_testes](#test)
4. [Executar projeto](#run)
5. [Melhorias](#mel)


## Introdução <a name="intr"></a>

Esse serviço é responsável por consumir eventos de jobs no kafka que devem ser organizados em uma janela de tempo. 
Ele organiza os jobs levando-se em consideração a data limite para a execução e uma janela de 8 horas para execução diária.

## Configuração de ambiente <a name="cs1"></a>

O projeto é um consumidor de kafka, então primeiro temos que subir o kafka. Para facilitar no projeto encontra-se 
um docker-compose já pronto.

Para subir a stack do kafka basta executar:
````bash
docker-compose up -d
````

Com o kafka rodando, temos que criar o topic. Para isso execute:

````bash
docker-compose exec kafka  \
kafka-topics --create --topic jobs --partitions 1 --replication-factor 1 --if-not-exists --zookeeper localhost:32181
````

Depois disso temos que instalar as dependências do projeto. Para isso execute:
````bash
mvn clean install
````

## Executar Testes Unitários <a name="test"></a>
Para executar os testes basta exeuctar:
````bash
mvn test
````

## Executar Projeto <a name="run"></a>
Para iniciar o consumidor basta executar:
````bash
mvn clean compile exec:java
````

Com o projeto rodando agora vamos publicar a mensagem no kafka para nosso consumidor. Para isso execute:
````bash
docker-compose exec kafka bash -c "echo '[{\"ID\": 1,\"Descrição\": \"Importação de arquivos de fundos\",\"Data Máxima de conclusão\": \"2021-01-28 10:00:00\",\"Tempo estimado\": \"2\"},{\"ID\": 2,\"Descrição\": \"Importação de dados da Base Legada\",\"Data Máxima de conclusão\": \"2021-01-29 12:00:00\",\"Tempo estimado\": \"4\"},{\"ID\": 3,\"Descrição\": \"Importação de dados de integração\",\"Data Máxima de conclusão\": \"2021-01-29 08:00:00\",\"Tempo estimado\": \"6\"}]' | kafka-console-producer --request-required-acks 1 --broker-list localhost:29092 --topic jobs"
````

## Melhorias Futuras <a name="mel"></a>
Com a limitação de tempo, algumas modificações legais ficaram pra depois, como:
* Um mecanismo de saída para os jobs organizados, já que hoje só são printados como:
    * publicar novamente no kafka em um novo topic
    * persistir em um banco de dados
* Alerta dizendo quais os jobs não conseguiram ser adicionados no agendamento
* Dockeriozação do projeto
* CI automático disparado pelo commit, com steps como:
    * lint de código
    * teste de complexidade de métodos
    * execução dos testes unitários
    * build de uma imagem docker
    * publish da imagem em um repositório como docker hub