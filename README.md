####Referentiel

kafka-console-producer.sh --broker-list localhost:9092 --topic referentiel --property parse.key=true --property key.separator=,

```
1,{"id":1, "name":"produit1"}
2,{"id":2, "name":"produit2"}
3,{"id":3, "name":"produit3"}
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
