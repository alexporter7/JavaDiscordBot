package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.DiscordBot;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class BotObjectHandler<T> {

    private ArrayList<T> objects;
    private String fileLocation;
    private boolean init;

    public BotObjectHandler(String fileLocation) {
        this.objects = new ArrayList<>();
        this.fileLocation = fileLocation;
    }

    public void serialize() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(fileLocation);
            gson.toJson(this.objects, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deserialize() {
        Gson gson = new GsonBuilder().create();
        try {
            FileReader fileReader = new FileReader(DiscordBot.OPTION_HANDLER_JSON);
            this.objects = gson.fromJson(fileReader,
                    new TypeToken<ArrayList<T>>(){ }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addObject(T object) {
        this.objects.add(object);
    }

    public ArrayList<T> getObjects() {
        return this.objects;
    }

    public boolean hasInit() {
        return this.init;
    }

    public void init() {
        this.init = true;
    }

}
