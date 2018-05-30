# Apache Kafka
[Kafka]() is a distributed streaming platform. It has three key capabilities:
* Publish and subscribe to streams of records, similar to a message queue or enterprise messaging system.
* Store streams of records in a fault-tolerant durable way.
* Process streams of records as they occur.

## Summary
* Kafka is run as a cluster on one or more servers that can span multiple data centers.
* The Kafka cluster stores streams of records in categories called topics.
* Each record consists of a key, a value, and a timestamp.
* Use the concept of topic in which records are published and consumed by multi-subscribers.
* Each topic has a a partitioned log where each partition is ordered immutable sequence of records.
* Each partition is replicated across a configurable number of servers for fault tolerance
* It uses topics with a pub/sub combined with queue model: it uses the concept of consumer group to divide the processing over a collection of consumer processes, and message can be broadcasted to multiple groups.
* Stream processing is helpful for handling out-of-order data, *reprocessing* input as code changes, and performing stateful computations. It uses producer / consumer. stateful storage and consumer groups. It treats both past and future data the same way.

### Stream details
* Continuous real time flow of records
* records are key-value pairs
* stream APIs transform, aggregate and enrich data, per record with milli second latency, from one topic to another one.
* support stateful and windowing operations
* Can be integrated in java application and micro service. No need for separate processing cluster. It is a Java API. Stream app is done outside of the broker code!.
* Elastic, highly scalable, fault tolerance
* Deploy to container



## Run Kafka in docker
We used the [Spotify kafka image](https://hub.docker.com/r/spotify/kafka/) from dockerhub as it includes [Zookeeper]() and Kafka in a single image.

It is started in background (-d)m named "kafka" and mounting
```
docker run -d -p 2181:2181 -p 9092:9092 -v `pwd`:/scripts --env ADVERTISED_HOST=`docker-machine ip \`docker-machine active\`` --name kafka --env ADVERTISED_PORT=9092 spotify/kafka
```

Then remote connect to the docker running instance to open a shell:
```
docker exec  -ti kafka /bin/bash
```

Create a topic: it uses zookeeper as a backend to persist persist partition within the topic.

```
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic mytopic
./kafka-topics.sh --list --zookeeper localhost:2181
```
We have done shell scripts for you to do those command and test your local kafka and then the kafka deployed on ICP. The scripts are under ../scripts/kafka
* createtopic.sh
* listtopic.sh
* sendText.sh  Send a multiple lines message on mytopic topic- open this one in one terminal.
* consumeMessage.sh  Connect to the topic to get messages. and this second in another terminal.

## Install on ICP

## Streaming app
The Java code in the stream.examples folder is using the quickstart code based. Code for processing event does:
* set a properties object to specify which brokers to connect to and what kind of serialization to use.
* Define a stream client: if you want stream of record use KStream, if you want a changelog with the last value of a given key use KTable (Example is to keep a user profile with userid as key)
* create a topology of input source and sink target
* start the stream client

### Example to run the Word Count application:
1. Be sure to create the needed different topics once the kafka broker is started (mytopic, streams-plaintext-input, streams-linesplit-output, streams-pipe-output, streams-wordcount-output):
```
docker exec -ti kafka /bin/bash
cd /scripts
./createtopics.sh
```

1. Start a terminal window and execute the command to be ready to send message.
```
$ docker exec -ti kafka /bin/bash
# can use the /scripts/openProducer.sh or...
root> /opt/kafka_2.11-0.10.1.0/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input
```

1. Start another terminal to listen to the output topic:
```
$ docker exec -ti kafka /bin/bash
# can use the /scripts/consumeWordCount.sh or...
root> /opt/kafka_2.11-0.10.1.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-wordcount-output --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```

1. Start the stream client to count word in the entered lines

```
mvn exec:java -Dexec.mainClass=ibm.cte.kafka.play.WordCount
```

Outputs of the WordCount application is actually a continuous stream of updates, where each output record is an updated count of a single word. A KTable is counting the occurrence of word, and a KStream send the output message with updated count.

### Stream use cases
* Aggregation of event coming from multiple producers

## compendium
* [Stream API](https://kafka.apache.org/11/documentation/streams/)
