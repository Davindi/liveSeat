package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LiveSeatConfigurationManager {
    private static final String configFileName = "config.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveConfiguration(LiveSeatConfiguration config) {
        try(FileWriter writer = new FileWriter(configFileName)){
            gson.toJson(config, writer);
            System.out.println("Saved configuration to " + configFileName);
        }catch(IOException e){
//            e.printStackTrace();
            System.err.println("Error save configuration  " + e.getMessage());
        }
    }
    public static LiveSeatConfiguration loadConfiguration() {
        try(FileReader reader = new FileReader(configFileName)){
            return gson.fromJson(reader, LiveSeatConfiguration.class);
        }catch(IOException e){
            System.err.println("Error load configuration  " + e.getMessage());
            return null;
        }
    }
}
