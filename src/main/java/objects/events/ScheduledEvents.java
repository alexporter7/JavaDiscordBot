package objects.events;


import main.DiscordBot;
import objects.DiscordLogger;
import objects.weather.WeatherObject;

import java.util.concurrent.TimeUnit;

public class ScheduledEvents {

    public static void initializeEvents() {

        //======= Register Repeating Events =======
        //Weather Event
        DiscordBot.scheduledEventHandler.registerRepeatingEvent(
                new ScheduledEvent(
                        "Weather Command",
                        "Gives the current weather at UCF",
                        () -> {
                            WeatherObject weatherObject = new WeatherObject();
                            DiscordBot.jda.getTextChannelsByName("weather", true).get(0)
                                    .sendMessage(weatherObject.pollWeather()).queue();},
                        0, 2, TimeUnit.HOURS)
        );


        //======= Register Repeating Events =======
        DiscordBot.scheduledEventHandler.registerOneTimeEvent(
                new ScheduledEvent(
                        "Discord Logger Delayed Execution",
                        "Allows the JDA to build and initialize before initializing the logger",
                        () -> {
                            DiscordBot.discordLogger = new DiscordLogger();
                        }, 5, 5, TimeUnit.SECONDS
                )
        );
    }

}
