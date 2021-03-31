package objects.lists;

import java.util.ArrayList;

public class BotList {

    private String listName;
    private ArrayList<BotListObject> items;

    public BotList() {
        this.listName = "no_name";
        this.items = new ArrayList<BotListObject>();
    }

    public BotList(String listName) {
        this.listName = listName;
        this.items = new ArrayList<BotListObject>();
    }

    public BotList(String listName, ArrayList<BotListObject> items) {
        this.listName = listName;
        this.items = items;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<BotListObject> getItems() {
        return items;
    }

    public void setItems(ArrayList<BotListObject> items) {
        this.items = items;
    }
}
