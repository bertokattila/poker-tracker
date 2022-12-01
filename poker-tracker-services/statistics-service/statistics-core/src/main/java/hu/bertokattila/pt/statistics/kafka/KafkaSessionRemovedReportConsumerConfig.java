package hu.bertokattila.pt.statistics.kafka;

import hu.bertokattila.pt.session.ExtendedSessionDTO;
import hu.bertokattila.pt.session.SessionRemovedDTO;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class KafkaSessionRemovedReportConsumerConfig {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public NewTopic topic2() {
    return TopicBuilder.name("sessionRemovedReport")
            .build();
  }

  @Bean
  public Map<String, Object> consumerConfig2() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SessionReportDeSerializer.class);

    return props;
  }

  @Bean
  public ConsumerFactory<String, SessionRemovedDTO> consumerFactory2() {
    return new DefaultKafkaConsumerFactory<>(consumerConfig2());
  }

  // Creating a Listener
  public ConcurrentKafkaListenerContainerFactory
  concurrentKafkaListenerContainerFactory()
  {
    ConcurrentKafkaListenerContainerFactory<
            String, SessionRemovedDTO> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory2());
    return factory;
  }

}
