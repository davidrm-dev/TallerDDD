package uptc.edu.swii.order.shared.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import uptc.edu.swii.order.shared.infrastructure.utils.JsonUtils;

@Component
public class EventProducer <T> {
    
    @Autowired
    private KafkaTemplate <String, String> kafkaTemplate;

    public void sendEvent(String topic, T event){
        String jsonEvent = JsonUtils.toJson(event);
        System.out.println("Producing event to topic: " + topic + " with payload: " + jsonEvent);
        kafkaTemplate.send(topic, jsonEvent);
    }
}