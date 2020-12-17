package Simulator;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class SimulationStartUp extends Application {

    public static Config config;


    @Override
    public void start(Stage primaryStage) throws Exception {

        config = readConfigFromJson(".\\src\\main\\java\\Simulator\\params\\parameters.json");
        new SimulationSetUp(primaryStage,config);
    }

    public static void crateSimulation(Stage stage){
        new SimulationSetUp(stage,config);
    }


    public Config readConfigFromJson(String filename){
        System.out.print(filename);
        Gson gson = new Gson();
        Config config;
        try {
            JsonReader reader = new JsonReader(new FileReader(filename));
            config = gson.fromJson(reader,Config.class);
        }catch(FileNotFoundException e){
            config = new Config();
            e.printStackTrace();
        }
        return config;
    }


}
