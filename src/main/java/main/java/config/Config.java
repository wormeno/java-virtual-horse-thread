package main.java.config;

import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Data
public class Config {

    private Properties properties = new Properties();

    private Integer metersOfRace;
    private Integer metersEnhancer;
    private Integer minResistanceHorse;
    private Integer maxResistanceHorse;
    private Integer minSpeedHorse;
    private Integer maxSpeedHorse;
    private Integer refresTimeEnhancerZone;
    private Integer plusEnhancerZone;
    private Integer sleepEnhancerZone;

    public Config() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File("src/main/resources/application.properties")));

            metersOfRace = Integer.valueOf(properties.get("METERS_OF_RACE").toString());
            metersEnhancer = Integer.valueOf(properties.get("METERS_ENHANCER").toString());

            minSpeedHorse = Integer.valueOf(properties.get("MIN_SPPED_HORSE").toString());
            maxSpeedHorse = Integer.valueOf(properties.get("MAX_SPEED_HORSE").toString());
            minResistanceHorse = Integer.valueOf(properties.get("MIN_RESISTANCE_HORSE").toString());
            maxResistanceHorse = Integer.valueOf(properties.get("MAX_RESISTANCE_HORSE").toString());
            refresTimeEnhancerZone = Integer.valueOf(properties.get("REFRESH_TIME_ENHANCER_ZONE").toString());
            plusEnhancerZone = Integer.valueOf(properties.get("PLUS_ENHANCER_ZONE").toString());
            sleepEnhancerZone = Integer.valueOf(properties.get("SLEEP_ENHANCER_ZONE").toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
