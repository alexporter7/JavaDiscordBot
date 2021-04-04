package objects.weather;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.Config;
import util.WeatherDataParser;

import java.util.Objects;

public class WeatherObject {

    private final OkHttpClient httpClient;
    private final Request request;

    public WeatherObject() {
        this.httpClient = new OkHttpClient();
        this.request = Config.WEATHER_REQUEST;
    }

    public MessageEmbed pollWeather() {
        try {
            Response response = this.httpClient.newCall(request).execute();
            WeatherDataParser weatherDataParser = new WeatherDataParser(response);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder
                    .setTitle("Weather at UCF")
                    .addField("Current Weather: ", weatherDataParser.getWeatherDescription(), true)
                    .addField("Temperature Outside: ", weatherDataParser.getCurrentTemperature(), true)
                    .addField("Humidity(%): ", weatherDataParser.getCurrentHumidity(), true);
            return embedBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EmbedBuilder()
                .setTitle("Weather Data Error")
                .addField("Response", "Weather could not be parsed", true)
                .build();
    }

}
