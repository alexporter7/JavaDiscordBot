package main;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.AboutCommand;
import commands.GetWeatherCommand;
import commands.ListCommand;
import commands.OptionCommand;
import exception.DiscordTokenFail;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import objects.calls.BotCallHandler;
import objects.calls.BotCallObject;
import objects.lists.BotList;
import objects.lists.BotListHandler;
import objects.lists.BotListObject;
import objects.options.BotOption;
import util.Config;
import util.DiscordToken;
import objects.options.OptionHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Alex Porter
 * JavaDiscordBot is a bot being built to test functionality and attempt to program a bot in Java versus Python.
 * To be able to use it a Config.java file must be placed in the "util" package with the Discord Token along with
 * any other static and final variables referenced throughout the script.
 */
public class DiscordBot {

    public static JDA jda = null;

    public static OptionHandler optionHandler = new OptionHandler();
    public static BotListHandler botListHandler = new BotListHandler();
    public static BotCallHandler botCallHandler = new BotCallHandler();

    public static void main(String[] args) {
        //Attempt to build the JDA Object, if it fails throw an exception
        try {
            buildJda();
            initializeOptions();
            initializeLists();
            initializeCallHandler();
        } catch (DiscordTokenFail discordTokenFail) {
            discordTokenFail.printStackTrace();
        }

    }

    /**
     * Attempts to build the JDA using the token provided
     * @throws DiscordTokenFail Invalid token or issue with the service
     */
    public static void buildJda() throws DiscordTokenFail {
        DiscordToken discordToken = new DiscordToken(Config.TOKEN);
        EventWaiter eventWaiter = new EventWaiter();
        CommandClientBuilder commandClientBuilder = new CommandClientBuilder();

        commandClientBuilder.setActivity(Activity.watching("Outdated tutorial in a foreign language"));
        commandClientBuilder.setOwnerId(Config.OWNER_ID);
        commandClientBuilder.setPrefix("$");
        commandClientBuilder.addCommands(
                //About Command
                generateAboutCommand(),
                //Weather Command
                new GetWeatherCommand(eventWaiter),
                //Option Command
                new OptionCommand(eventWaiter),
                //List Command
                new ListCommand(eventWaiter)
        );

        try {
            jda = JDABuilder.createDefault(discordToken.getToken())
                    .addEventListeners(eventWaiter, commandClientBuilder.build())
                    .build();
        } catch (Exception e) {
            throw new DiscordTokenFail();
        }
    }

    /**
     * Generates the About command, saves space when initializing all the commands for the bot
     * @return - (AboutCommand) the about command object
     */
    private static AboutCommand generateAboutCommand() {
        return new AboutCommand(Color.BLUE, "Java Bot",
                new String[] {"Command list", "Will be", "Here"},
                Permission.ADMINISTRATOR);
    }

    /**
     * Initializes all the options that will be added into the bot
     */
    private static void initializeOptions() {
        optionHandler.addOption(new BotOption<String>("test option 1", "string option", "value1"));
        optionHandler.addOption(new BotOption<Boolean>("test option 2", "boolean option", false));
    }

    /**
     * Checks for serialized lists and loads them if necessary
     */
    private static void initializeLists() {
        ArrayList<BotListObject> objects = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            objects.add(new BotListObject("test item " + i, "test description " + i));
        }
        BotList botList = new BotList("list 1", objects);
        botListHandler.addList(botList);
        botListHandler.serializeList();
    }

    /**
     * Checks for serialized call logs
     */
    private static void initializeCallHandler() {
        if(!botCallHandler.hasInit()) {
            botCallHandler.deserializeData();
        }

        //TESTING DATA
        for(int i = 0; i < 5; i++) {
            botCallHandler.addCall(new BotCallObject("test call " + i));
        }

        botCallHandler.serializeData();
        System.out.println(Arrays.toString(botCallHandler.getCalls().toArray()));
    }

}
