package objects.options;

/**
 * @author Alex Porter
 * @param <T> - Type for the value the option will hold
 */
public class BotOption<T> {

    private String name;
    private String description;
    private T value;

    public BotOption(String name, String description, T value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
