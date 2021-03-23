package util;

import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Alex Porter
 */
public class WeatherDataParser {

    private final JSONObject json;

    public WeatherDataParser(Response weatherResponse) throws IOException {
        this.json = new JSONObject(weatherResponse.body().string());
    }

    public String getWeatherDescription() {
        return this.json.getJSONArray("weather")
                .getJSONObject(0)
                .getString("description");
    }

    public String getCurrentTemperature() {
        return String.valueOf(this.json.getJSONObject("main")
                .getDouble("temp"));
    }

    public String getCurrentHumidity() {
        return String.valueOf(this.json.getJSONObject("main")
                .getInt("humidity"));
    }

}
