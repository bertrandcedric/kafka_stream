#### Kafka + prometheus + Grafana

start kafka + jmx-exporte => 
KAFKA_OPTS=-javaagent:/usr/app/jmx_prometheus_javaagent.jar=<LISTEN_PORT>:<PATH_TO_CONFIG_FILE>
KAFKA_OPTS=-javaagent:./jmx_prometheus_javaagent-0.9.jar=1234:jmx_exporter-kafka-broker.yml


####Referentiel

kafka-console-producer.sh --broker-list localhost:9092 --topic referentiel --property parse.key=true --property key.separator=,

```
1,{"id":1, "name":"produit1"}
2,{"id":2, "name":"produit2"}
3,{"id":3, "name":"produit3"}
4,{"id":4, "name":"produit4"}
5,{"id":5, "name":"produit5"}
6,{"id":6, "name":"produit6"}
7,{"id":7, "name":"produit7"}
11,{"id":11, "name":"produit11"}
```

####Achats

kafka-console-producer.sh --broker-list localhost:9092 --topic achats

```
{"id": 1, "price": 3.45}
{"id": 2, "price": 13.40}
{"id": 30, "price": 1.05}
{"id": 1, "price": 3.40}
```

####Recuperation sur le topic achats-by-product-id

```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic achats-by-product-id --consumer.config config/consumer.properties
```

####Interrogation du referentiel

```
http://localhost:7077/kafka/referentiels
```

####Build

Pour tester la version java (utilisation du profil par defaut)
```
mvn clean package -P java
```

Pour tester la version scala (utilisation du profil scala)
```
mvn clean package -P scala
```
