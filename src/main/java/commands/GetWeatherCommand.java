package commands;

import main.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import objects.weather.WeatherObject;
import util.Config;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.WeatherDataParser;

import java.io.IOException;
import java.util.Objects;

public class GetWeatherCommand extends Command {

    private final EventWaiter eventWaiter;

    public GetWeatherCommand(EventWaiter eventWaiter) {
        this.eventWaiter = eventWaiter;
        this.name = "get_weather";
        this.aliases = new String[] {"weather"};
        this.help = "Allows you to grab the weather in a predefined location";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        commandEvent.reply("Give me a second while I grab the current data");
        WeatherObject weatherObject = new WeatherObject();
        Objects.requireNonNull(
                commandEvent.getJDA()
                        .getTextChannelById(Config.WEATHER_CHANNEL))
                        .sendMessage(weatherObject.pollWeather())
                        .queue();
    }
}
