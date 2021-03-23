package main;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.AboutCommand;
import commands.GetWeatherCommand;
import commands.OptionCommand;
import exception.DiscordTokenFail;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import objects.BotOption;
import util.Config;
import util.DiscordToken;
import util.OptionHandler;

import java.awt.*;

/**
 * @author Alex Porter
 */
public class DiscordBot {

    public static JDA jda = null;
    public static OptionHandler optionHandler = new OptionHandler();

    public static void main(String[] args) {
        //Attempt to build the JDA Object, if it fails throw an exception
        try {
            buildJda();
            initializeOptions();
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
                new OptionCommand(eventWaiter)
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

}
