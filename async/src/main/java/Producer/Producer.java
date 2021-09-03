package Producer;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer {
	public static void main(String[] args) {
		Properties properties = new Properties();
		
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties, new StringSerializer(), new StringSerializer());
		
		try {
			for(int i=0; i<10; i++) {
				producer.send(new ProducerRecord<String, String>("Topic1", String.format("message%02d", i)));
			}
		}finally {
			producer.close();
		}
	}

}
