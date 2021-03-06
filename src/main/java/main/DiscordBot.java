package main;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.AboutCommand;
import commands.*;
import exception.DiscordTokenFail;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import objects.DiscordLogger;
import objects.calls.BotCallHandler;
import objects.events.ScheduledEventHandler;
import objects.events.ScheduledEvents;
import objects.lists.BotListHandler;
import objects.options.BotOption;
import objects.options.OptionHandler;
import util.Config;
import util.DiscordToken;

import java.awt.*;
import java.util.ArrayList;


/**
 * @author Alex Porter
 * JavaDiscordBot is a bot being built to test functionality and attempt to program a bot in Java versus Python.
 * To be able to use it a Config.java file must be placed in the "util" package with the Discord Token along with
 * any other static and final variables referenced throughout the script.
 */
public class DiscordBot {

    /*
     * Stuff I want to Add
     * CatFacts API as a Scheduled Event
     * https://cat-fact.herokuapp.com/facts
     */

    public static JDA jda = null;

    public static OptionHandler optionHandler = new OptionHandler();
    public static BotListHandler botListHandler = new BotListHandler();
    public static BotCallHandler botCallHandler = new BotCallHandler();
    public static ScheduledEventHandler scheduledEventHandler = new ScheduledEventHandler();
    public static DiscordLogger discordLogger;

    public static final String OPTION_HANDLER_JSON = "options.json";
    public static final String LIST_HANDLER_JSON = "lists.json";

    //public static BotObjectHandler<BotOption> optionHandler = new BotObjectHandler(OPTION_HANDLER_JSON);

    public static ArrayList<String> validOptions = new ArrayList<>();

    public static void main(String[] args) {
        //Attempt to build the JDA Object, if it fails throw an exception
        try {
            buildJda();
            initializeOptions();
            initializeLists();
            initializeCallHandler();
            initializeScheduledEventHandler();
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
                new ListCommand(eventWaiter),
                //Call Command
                new LogCallCommand(eventWaiter),
                //Scheduled Events Command
                new ScheduledTaskCommand(eventWaiter)
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
        if(!optionHandler.hasInit()) {
            optionHandler.deserializeOptions();
            optionHandler.init();
        }
        if(optionHandler.getOptions().size() == 0)
            createDefaultOptions();
        optionHandler.serializeOptions();
        addValidOptions();
    }

    /**
     * Checks for serialized lists and loads them if necessary
     */
    private static void initializeLists() {
        if(!botListHandler.hasInit()) {
            botListHandler.deserializeList();
            botListHandler.init();
        }
        botListHandler.serializeList();
    }

    /**
     * Checks for serialized call logs
     */
    private static void initializeCallHandler() {
        if(!botCallHandler.hasInit()) {
            botCallHandler.deserializeData();
            botCallHandler.init();
        }
        botCallHandler.serializeData();
    }

    /**
     * Adds valid options
     */
    private static void addValidOptions() {
        optionHandler
                .getOptions()
                .forEach(
                        s -> {validOptions.add(s.getName());
                        });
    }

    private static void createDefaultOptions() {
        optionHandler.addOption(new BotOption("useScheduledEvents", "Tells the bot to initialize scheduled events", "true"));
        optionHandler.addOption(new BotOption("enableLogging", "Enables logging to be done and sent through a specified channel", "true"));
        optionHandler.addOption(new BotOption("loggingChannel", "The defined logging channel to send log messages", "log"));
    }

    private static void initializeScheduledEventHandler() {
        ScheduledEvents.initializeEvents();
        if(!scheduledEventHandler.hasInit()
                && optionHandler.getOption("useScheduledEvents").getValue().equals("true")) {
            scheduledEventHandler.initialize();
        }
    }

}
