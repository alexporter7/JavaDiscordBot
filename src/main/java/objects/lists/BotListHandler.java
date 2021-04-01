package objects.lists;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BotListHandler {

    private boolean init = false;
    private ArrayList<BotList> lists;

    public BotListHandler() {
        this.lists = new ArrayList<BotList>();
    }

    public boolean hasInit() {
        return this.init;
    }

    public void deserializeList() {
        //TODO
    }

    public void serializeList() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter("test.json");
            gson.toJson(this.lists, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BotList> getLists() {
        return lists;
    }

    public void addList(BotList list) {
        this.lists.add(list);
    }

}
