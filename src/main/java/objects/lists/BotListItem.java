package objects.lists;

import com.google.gson.annotations.SerializedName;

public class BotListItem {

    @SerializedName("itemName")
    private String itemName;

    @SerializedName("description")
    private String description;

    public BotListItem(String itemName) {
        this.itemName = itemName;
    }

    public BotListItem(String itemName, String description) {
        this.itemName = itemName;
        this.description = description;
    }

    public boolean hasDescription() {
        return this.description != null;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return this.description == null ? null : this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
