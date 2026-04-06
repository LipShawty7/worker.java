import com.fasterxml.jackson.databind.JsonNode;

public class Task {
    private String id;
    private int attempts;
    private JsonNode payload;

    public String getId() { return id; }
    public int getAttempts() { return attempts; }

    public JsonNode getPayload() {
        return payload;
    }
}