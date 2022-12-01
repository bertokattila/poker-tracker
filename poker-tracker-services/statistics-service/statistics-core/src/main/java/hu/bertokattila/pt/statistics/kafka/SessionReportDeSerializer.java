package hu.bertokattila.pt.statistics.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bertokattila.pt.session.ExtendedSessionDTO;
import hu.bertokattila.pt.session.SessionRemovedDTO;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class SessionReportDeSerializer implements Deserializer<Object> {
  private ObjectMapper objectMapper = new ObjectMapper();

  public SessionReportDeSerializer(){
    super();
    objectMapper.findAndRegisterModules();
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
  }

  @Override
  public Object deserialize(String topic, byte[] data) {
    try {
      if (data == null){
        System.out.println("Null received at deserializing");
        return null;
      }
      System.out.println("Deserializing...");
      objectMapper.findAndRegisterModules();
      if(topic.equals("sessionRemovedReport")){
        return objectMapper.readValue(new String(data, "UTF-8"), SessionRemovedDTO.class);
      }
      return objectMapper.readValue(new String(data, "UTF-8"), ExtendedSessionDTO.class);

    } catch (Exception e) {
      throw new SerializationException("Error when deserializing byte[] to ExtendedSessionDTO");
    }
  }

  @Override
  public void close() {
  }
}
