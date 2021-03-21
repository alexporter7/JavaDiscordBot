package commands;

import net.dv8tion.jda.api.entities.Message;
import util.Config;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.WeatherDataParser;

import java.io.IOException;

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
        OkHttpClient httpClient = new OkHttpClient();
        Request request = Config.WEATHER_REQUEST;
        try {
            Response response = httpClient.newCall(request).execute();
            WeatherDataParser weatherDataParser = new WeatherDataParser(response);
            commandEvent.reply(String.format(
                    "Current weather is `%s`%n" +
                            "Temperature outside is `%s`%n" +
                            "Humidity right now is `%s`",
                    weatherDataParser.getWeatherDescription(),
                    weatherDataParser.getCurrentTemperature(),
                    weatherDataParser.getCurrentHumidity()
            ));

        } catch (IOException e) {
            e.printStackTrace();
            commandEvent.reply("Unable to grab current weather data");
        }
    }
}
