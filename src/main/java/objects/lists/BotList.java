package objects.lists;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BotList {

    @SerializedName("listName")
    private String listName;

    @SerializedName("items")
    private ArrayList<BotListItem> items;

    public BotList() {
        this.listName = "no_name";
        this.items = new ArrayList<BotListItem>();
    }

    public BotList(String listName) {
        this.listName = listName;
        this.items = new ArrayList<BotListItem>();
    }

    public BotList(String listName, ArrayList<BotListItem> items) {
        this.listName = listName;
        this.items = items;
    }

    public void addItem(BotListItem item) {
        this.items.add(item);
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<BotListItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<BotListItem> items) {
        this.items = items;
    }
}
