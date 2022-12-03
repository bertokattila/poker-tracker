package hu.bertokattila.pt.session.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bertokattila.pt.session.ExtendedSessionDTO;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class SessionReportSerializer implements Serializer<ExtendedSessionDTO> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public SessionReportSerializer() {
    super();
    objectMapper.findAndRegisterModules();
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
  }

  @Override
  public byte[] serialize(String topic, ExtendedSessionDTO data) {
    try {
      if (data == null) {
        System.out.println("Null received at serializing");
        return null;
      }
      System.out.println("Serializing...");
      return objectMapper.writeValueAsBytes(data);
    } catch (Exception e) {
      throw new SerializationException("Error when serializing ExtendedSessionDTO to byte[]");
    }
  }

  @Override
  public void close() {
  }
}
