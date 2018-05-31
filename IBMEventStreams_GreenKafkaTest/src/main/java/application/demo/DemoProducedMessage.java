package application.demo;

import javax.json.Json;
import javax.json.JsonObject;


public class DemoProducedMessage {

    private String customContent;
    private final String message_type = "sample message";
    private long timestamp;

    public DemoProducedMessage(String customContent) {
        this.customContent = customContent;
        this.timestamp = System.currentTimeMillis();
    }

    public String toString() {
        JsonObject object = Json.createObjectBuilder()
            .add("timestamp", timestamp)
            .add("message_type", message_type)
            .add("custom", customContent)
            .build();
        return object.toString();
    }

}