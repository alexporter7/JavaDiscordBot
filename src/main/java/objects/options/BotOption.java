package objects.options;

import com.google.gson.annotations.SerializedName;

/**
 * @author Alex Porter
 */
public class BotOption {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("value")
    private String value;

    public BotOption(String name, String description, String value) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
