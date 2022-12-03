package hu.bertokattila.pt.session.kafka;

import hu.bertokattila.pt.session.SessionRemovedDTO;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaSessionRemovedReportProducer {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public NewTopic removedTopic() {
    return TopicBuilder.name("sessionRemovedReport")
            .build();
  }

  @Bean
  public Map<String, Object> producerConfig2() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "hu.bertokattila.pt.session.kafka.SessionRemovedReportSerializer");
    return props;
  }

  @Bean
  public ProducerFactory<String, SessionRemovedDTO> producerFactory2() {
    return new DefaultKafkaProducerFactory<>(producerConfig2());
  }

  @Bean(name = "removeTemplate")
  public KafkaTemplate<String, SessionRemovedDTO> kafkaTemplate() {
    return new KafkaTemplate<String, SessionRemovedDTO>(producerFactory2());
  }
}
