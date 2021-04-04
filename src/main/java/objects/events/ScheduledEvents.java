package objects.events;


import main.DiscordBot;
import objects.weather.WeatherObject;

import java.util.concurrent.TimeUnit;

public class ScheduledEvents {

    public static void initializeEvents() {

        //Weather Event
        DiscordBot.scheduledEventHandler.registerRepeatingEvent(
                new ScheduledEvent(
                        () -> {
                            WeatherObject weatherObject = new WeatherObject();
                            DiscordBot.jda.getTextChannelsByName("weather", true).get(0)
                                    .sendMessage(weatherObject.pollWeather()).queue();},
                        0, 2, TimeUnit.HOURS)
        );

    }

}
