package objects.options;

import objects.options.BotOption;

import java.util.ArrayList;

public class OptionHandler {

    private ArrayList<BotOption> options;

    public OptionHandler() {
        this.options = new ArrayList<>(); //Initialize the empty array list
    }

    public ArrayList<BotOption> getOptions() {
        return this.options;
    }

    public void addOption(BotOption botOption) {
        this.options.add(botOption);
    }

}
