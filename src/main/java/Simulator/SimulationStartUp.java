package Simulator;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;


public class SimulationStartUp extends Application {
    private GrassMap map;
    private IEngine engine;
    private StatTrack statTrack;
    private final int MAP_WIDTH = 20;
    private final int MAP_HEIGHT = 20;
    private final int JUNGLE_WIDTH = 12;
    private final int JUNGLE_HEIGHT = 12;
    private final float GRASS_ENERGY = 15;
    private final float ENERGY_LOSS = 1;
    public static final float START_ENERGY = 70;
    private final int START_NUMBER = 12;


    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeSimulation();

        BorderPane rootPane = new BorderPane();
        rootPane.setPrefSize(1000,600);

        MapView mapView = new MapView(this.map);
        mapView.setPrefSize(800,600);


        SimulatorControl simulatorControl = new SimulatorControl(this.engine,mapView);

        StatPanel statPanel = new StatPanel(simulatorControl,this.map,this.statTrack);
        statPanel.setPrefSize(200,600);

        simulatorControl.setStatPanel(statPanel);




        rootPane.setLeft(statPanel);
        rootPane.setRight(mapView);
        BorderPane.setAlignment(mapView,Pos.CENTER);

        Scene scene = new Scene(rootPane,1000,600);


        primaryStage.setScene(scene);
        primaryStage.show();



    }

    private void initializeSimulation(){

        Vector2d mapSize = new Vector2d(MAP_WIDTH,MAP_HEIGHT);
        Vector2d jungleSize = new Vector2d(JUNGLE_WIDTH,JUNGLE_HEIGHT);
        this.map = new GrassMap(mapSize,jungleSize,GRASS_ENERGY);


        Vector2d[] startPositions = new Vector2d[START_NUMBER];
        Random randomizer = new Random();
        for(int i = 0; i < START_NUMBER;i++){
            int x = randomizer.nextInt(mapSize.x);
            int y = randomizer.nextInt(mapSize.y);
            Vector2d v = new Vector2d(x,y);
            startPositions[i] = v;
        }
        this.statTrack = new StatTrack(map);
        this.engine = new SimulationEngine(map,startPositions,ENERGY_LOSS,START_ENERGY,this.statTrack);

    }
}
