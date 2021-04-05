package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import main.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import objects.events.ScheduledEvent;
import util.EmbedHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskCommand extends Command {

    private final EventWaiter eventWaiter;

    public ScheduledTaskCommand(EventWaiter eventWaiter) {
        this.eventWaiter = eventWaiter;
        this.name = "tasks";
        this.aliases = new String[] {"st"};
        this.help = "Gives information about scheduled events";
    }

    //The Stop, Start, and Restart commands are not working properly, will re-enable when implemented correctly
    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] args = commandEvent.getArgs().split(" ");
        switch(args[0]) {
            case "":
            case "info":
                viewScheduledEvents(commandEvent);
                break;
            case "stop":
                //stopScheduledEvents(commandEvent);
                break;
            case "start":
                //startScheduledEvents(commandEvent);
                break;
            case "reload":
            case "restart":
                //restartScheduledEvents(commandEvent);
                break;
            default:
                commandEvent.reply("Invalid arguments");

        }
    }

    private void restartScheduledEvents(CommandEvent commandEvent) {
        if(!DiscordBot.scheduledEventHandler.hasInit()) {
            EmbedBuilder embedBuilder =
                    EmbedHelper.generateSimpleEmbed(commandEvent, "The Scheduled Event Handler is already stopped please start before reloading");
            commandEvent.reply(embedBuilder.build());
            DiscordBot.discordLogger.logMessage(embedBuilder.build());
            return;
        }
        this.stopScheduledEvents(commandEvent);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(
                () -> { this.startScheduledEvents(commandEvent); }, 10, TimeUnit.SECONDS);
    }

    private void startScheduledEvents(CommandEvent commandEvent) {
        if(DiscordBot.scheduledEventHandler.hasInit()) {
            EmbedBuilder embedBuilder =
                    EmbedHelper.generateSimpleEmbed(commandEvent, "Scheduled Event Handler has already been initialized");
            commandEvent.reply(embedBuilder.build());
            DiscordBot.discordLogger.logMessage(embedBuilder.build());
            return;
        }
        EmbedBuilder embedBuilder =
                EmbedHelper.generateSimpleEmbed(commandEvent, "Scheduled Event Handler is now initializing");
        DiscordBot.scheduledEventHandler.initialize();
        DiscordBot.discordLogger.logMessage(embedBuilder.build());
        commandEvent.reply(embedBuilder.build());
    }

    private void stopScheduledEvents(CommandEvent commandEvent) {
        if(!DiscordBot.scheduledEventHandler.hasInit()) {
            EmbedBuilder embedBuilder =
                    EmbedHelper.generateSimpleEmbed(commandEvent, "The Scheduled Event Handler has not yet been initialized or has already been stopped");
            commandEvent.reply(embedBuilder.build());
            DiscordBot.discordLogger.logMessage(embedBuilder.build());
            return;
        }
        EmbedBuilder embedBuilder =
                EmbedHelper.generateSimpleEmbed(commandEvent, "Stopping Scheduled Events");
        DiscordBot.scheduledEventHandler.disableScheduledEvents();
        DiscordBot.discordLogger.logMessage(embedBuilder.build());
        commandEvent.reply(embedBuilder.build());
    }

    private void viewScheduledEvents(CommandEvent commandEvent) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        for(ScheduledEvent scheduledEvent : DiscordBot.scheduledEventHandler.getRepeatingEvents()) {
            embedBuilder
                    .addField("Event Name", scheduledEvent.getName(), true)
                    .addField("Event Description", scheduledEvent.getDescription(), true)
                    .addField("Initial Delay", String.valueOf(scheduledEvent.getInitialDelay()), true)
                    .addField("Scheduled Delay", String.valueOf(scheduledEvent.getDelay()), true)
                    .addField("Running", DiscordBot.scheduledEventHandler.hasInit() ? "True" : "False", true)
                    .addBlankField(false);
        }
        commandEvent.reply(embedBuilder.build());
        EmbedBuilder logEmbedBuilder =
                EmbedHelper.generateSimpleEmbed(commandEvent, "Successfully printed currently repeating scheduled tasks");
        DiscordBot.discordLogger.logMessage(logEmbedBuilder.build());
    }
}
