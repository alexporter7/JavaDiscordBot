import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.AboutCommand;
import commands.GetWeatherCommand;
import exception.DiscordTokenFail;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import util.Config;
import util.DiscordToken;

import java.awt.*;

/**
 * @author Alex Porter
 */
public class DiscordBot {

    public static JDA jda = null;

    public static void main(String[] args) {
        //Attempt to build the JDA Object, if it fails throw an exception
        try {
            buildJda();
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
                new GetWeatherCommand(eventWaiter)
        );

        try {
            jda = JDABuilder.createDefault(discordToken.getToken())
                    .addEventListeners(eventWaiter, commandClientBuilder.build())
                    .build();
        } catch (Exception e) {
            throw new DiscordTokenFail();
        }
    }

    public static AboutCommand generateAboutCommand() {
        return new AboutCommand(Color.BLUE, "Java Bot",
                new String[] {"Command list", "Will be", "Here"},
                Permission.ADMINISTRATOR);
    }


}
