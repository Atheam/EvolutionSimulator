package Simulator;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class SimulationSetUp {
    private GrassMap map;
    private IEngine engine;
    private StatTrack statTrack;
    Config config;


    public SimulationSetUp(Stage stage,Config config){
        this.config = config;
        initializeSimulation();
        stage.setTitle("Simulation");

        BorderPane rootPane = new BorderPane();

        rootPane.setPrefSize(1000,700);

        MapView mapView = new MapView(this.map,stage,config,config.getObjectSize());
        mapView.setStatTrack(this.statTrack);

        SimulatorControl simulatorControl = new SimulatorControl(this.engine,mapView,config);

        StatPanel statPanel = new StatPanel(simulatorControl,this.statTrack,mapView,stage);





        simulatorControl.setStatPanel(statPanel);


        rootPane.setLeft(statPanel);
        rootPane.setCenter(mapView);




        Scene scene = new Scene(rootPane,1000,700);
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {stage.setAlwaysOnTop(true); stage.setAlwaysOnTop(false);});

        stage.setScene(scene);
        stage.show();

    }
    private void initializeSimulation(){

        Vector2d mapSize = new Vector2d(config.getMapWidth(),config.getMapHeight());
        Vector2d jungleSize = new Vector2d(config.getJungleWidth(),config.getJungleHeight());
        this.map = new GrassMap(mapSize,jungleSize,config.getGrassEnergy());


        Vector2d[] startPositions = new Vector2d[config.getStartNumber()];
        Random randomizer = new Random();
        for(int i = 0; i < config.getStartNumber();i++){
            int x = randomizer.nextInt(mapSize.x);
            int y = randomizer.nextInt(mapSize.y);
            Vector2d v = new Vector2d(x,y);
            startPositions[i] = v;
        }
        this.statTrack = new StatTrack(map);
        this.engine = new SimulationEngine(map,startPositions,config.getEnergyLoss(),config.getStartEnergy(),this.statTrack);

    }
}
