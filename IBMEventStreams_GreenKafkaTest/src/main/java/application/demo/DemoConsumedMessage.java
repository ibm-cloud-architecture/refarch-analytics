package application.demo;

import javax.json.Json;

public class DemoConsumedMessage {

    private String topic;
    private int partition;
    private long offset;
    private String value;

    public DemoConsumedMessage(String topic, int partition, long offset, String value) {
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.value = value;
    }

    public String getTopic() {
        return topic;
    }

    public int getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }

    public String getValue() {
        return value;
    }

    public String encode() {
        return Json.createObjectBuilder()
            .add("topic", topic)
            .add("partition", partition)
            .add("offset", offset)
            .add("value", value)
            .build().toString();
    }
}