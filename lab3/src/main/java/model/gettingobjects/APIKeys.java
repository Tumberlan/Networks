package model.gettingobjects;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class APIKeys {
    private Properties properties;
    private static final String GRAPHHOPER_KEY = "GRAPHHOPPER_KEY";
    private static final String OPEN_TRIP_MAP_KEY = "OPEN_TRIP_MAP_KEY";
    private static final String OPEN_WEATHER_MAP_KEY = "OPEN_WEATHER_MAP_KEY";

    @Getter
    private String graphhopperKey;
    @Getter
    private String openTripMapKey;
    @Getter
    private String openWeatherMapKey;

    public APIKeys() {
        InputStream fis = null;
        try {
            properties = new Properties();
            fis = new FileInputStream("src\\main\\resources\\KEYS.properties");
            properties.load(fis);
            getAllKeys();
        } catch (FileNotFoundException e) {
            log.error("can't find key properties file");
        } catch (IOException e) {
            log.error("can't load info from file input stream");
        }

    }

    private void getAllKeys() {
        Set<Object> keys = properties.keySet();
        graphhopperKey = getPropertyValue(GRAPHHOPER_KEY);
        openTripMapKey = getPropertyValue(OPEN_TRIP_MAP_KEY);
        openWeatherMapKey = getPropertyValue(OPEN_WEATHER_MAP_KEY);
    }

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }


}
