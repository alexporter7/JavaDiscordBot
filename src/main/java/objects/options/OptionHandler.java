package objects.options;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.DiscordBot;
import objects.calls.BotCallObject;
import objects.options.BotOption;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class OptionHandler {

    private ArrayList<BotOption> options;
    private boolean init;

    public OptionHandler() {
        this.options = new ArrayList<>(); //Initialize the empty array list
        this.init = false;
    }

    public boolean hasInit() {
        return this.init;
    }

    public void init() {
        this.init = true;
    }

    public ArrayList<BotOption> getOptions() {
        return this.options;
    }

    public void addOption(BotOption botOption) {
        this.options.add(botOption);
    }

    public void serializeOptions() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(DiscordBot.OPTION_HANDLER_JSON);
            gson.toJson(this.options, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deserializeOptions() {
        Gson gson = new GsonBuilder().create();
        try {
            FileReader fileReader = new FileReader(DiscordBot.OPTION_HANDLER_JSON);
            this.options = gson.fromJson(fileReader,
                    new TypeToken<ArrayList<BotOption>>(){ }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
