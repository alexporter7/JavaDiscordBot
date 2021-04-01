package objects.lists;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.DiscordBot;
import objects.calls.BotCallObject;

import java.io.File;
import java.io.FileReader;
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

    public void init() {
        this.init = true;
    }

    public void deserializeList() {
        Gson gson = new GsonBuilder().create();
        try {
            FileReader fileReader = new FileReader(DiscordBot.LIST_HANDLER_JSON);
            this.lists = gson.fromJson(fileReader,
                    new TypeToken<ArrayList<BotList>>(){ }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void serializeList() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(DiscordBot.LIST_HANDLER_JSON);
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
