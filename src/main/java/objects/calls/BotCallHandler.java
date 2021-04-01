package objects.calls;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BotCallHandler {

    private ArrayList<BotCallObject> botCallObjectArrayList;
    private boolean init;

    public BotCallHandler() {
        this.botCallObjectArrayList = new ArrayList<BotCallObject>();
    }

    public void serializeData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter("callLog.json");
            gson.toJson(this.botCallObjectArrayList, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deserializeData() {
        Gson gson = new GsonBuilder().create();
        try {
            FileReader fileReader = new FileReader("callLog.json");
            this.botCallObjectArrayList = gson.fromJson(fileReader,
                    new TypeToken<ArrayList<BotCallObject>>(){ }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addCall(BotCallObject botCallObject) {
        this.botCallObjectArrayList.add(botCallObject);
    }

    public ArrayList<BotCallObject> getCalls() {
        return this.botCallObjectArrayList;
    }

    public boolean hasInit() {
        return this.init;
    }

    public void init() {
        this.init = true;
    }

}
