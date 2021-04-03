package objects.events;


import main.DiscordBot;

import java.util.concurrent.TimeUnit;

public class ScheduledEvents {

    public static void initializeEvents() {

        //Example
        DiscordBot.scheduledEventHandler.registerRepeatingEvent(
                new ScheduledEvent(
                        () -> {
                            DiscordBot.jda.getTextChannelsByName("general", true).get(0).sendMessage("Guess it works! (1)").queue(); },
                        5, 5, TimeUnit.SECONDS)
        );

    }

}
