package web.architecture;

/**
 * Used to transport messages back to the client.
 */
public class ResponseMessage {
    public enum Type {
        success, warn, error, info;
    }

    private final Type type;
    private final String text;
    private final Object data;

    public ResponseMessage(Type type, String text, Object data) {
        this.type = type;
        this.text = text;
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

}
