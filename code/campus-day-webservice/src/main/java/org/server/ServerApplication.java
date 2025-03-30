package org.server;

//import org.server.api.kafka.KafkaConsumerThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication ( exclude = {DataSourceAutoConfiguration.class } )
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		System.out.println(":::: Server Application ::::");

		// Start Kafka
		// KafkaConsumerThread kafkaConsumerThread = new KafkaConsumerThread("new_messages", "webservice", "kafka-service:9092");
		// kafkaConsumerThread.start();
	}

}
