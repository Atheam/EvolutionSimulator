package Simulator.GUI;

import Simulator.Simulation.Config.Config;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class SimulationStartUp extends Application {

    public static Config config;
    private final String PATH = ".\\src\\main\\java\\Simulator\\params\\parameters.json";

    @Override
    public void start(Stage primaryStage) throws Exception {
        config = readConfigFromJson(PATH);
        new SimulationSetUp(primaryStage,config);
    }

    public static void crateSimulation(Stage stage){
        new SimulationSetUp(stage,config);
    }

    public Config readConfigFromJson(String filename){
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
