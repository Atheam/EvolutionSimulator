package Simulator.GUI;


import javafx.event.ActionEvent;



import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;


public class StatPanel extends VBox {
    private SimulatorControl simulatorControl;
    private StatTrack statTrack;
    private MapView mapView;
    private Text animalsCount;
    private Text grassCount;
    private Text dominatingGenotype;
    private Text averageEnergy;
    private Text averageLifeSpan;
    private Text averageChildrenCount;
    private Text dayNumber;
    private Text statisticsTitle;
    private Stage stage;


    public StatPanel(SimulatorControl simulatorControl,StatTrack statTrack,MapView mapView,Stage stage) {
        this.stage = stage;
        this.statTrack = statTrack;
        this.simulatorControl = simulatorControl;
        this.mapView = mapView;


        Text panelTitle = new Text("Simulation Controls");
        panelTitle.setStyle("-fx-font-weight: bold");

        Button start = new Button("Start Simulation");
        start.setOnAction(this::handleStart);

        Button stop = new Button("Stop Simulation");
        stop.setOnAction(this::handleStop);

        Button bestAnimals = new Button("Color animals with best genotype");
        bestAnimals.setOnAction(this::colorBest);

        Button makeNewBoard = new Button("Make new board");
        makeNewBoard.setOnAction(this::makeNewBoard);

        Button checkTracked = new Button("Check tracked animal");
        checkTracked.setOnAction(this::checkTracked);

        Button saveToFile = new Button("Save current status to file");
        saveToFile.setOnAction(this.statTrack::saveToFile);
        saveToFile.setStyle(" margin-bottom: 50 ");

        this.getChildren().addAll(panelTitle,start,stop,bestAnimals,makeNewBoard,checkTracked,saveToFile);

        this.initializeStats();

        try {
            Text legendTitle = new Text("Legend: ");
            legendTitle.setStyle("-fx-font-weight: bold");
            Image legend = new Image(new FileInputStream(".\\src\\main\\java\\Simulator\\media\\legend.png"));
            ImageView legendView = new ImageView(legend);
            legendView.setFitHeight(150);
            legendView.setFitWidth(230);
            this.getChildren().addAll(legendTitle,legendView);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        setSpacing(10);
        setStyle("-fx-background-color: #EEEEEE");
        setBorder(new Border(new BorderStroke(Color.LIGHTBLUE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    }

    private void handleStart(ActionEvent actionEvent){

        simulatorControl.start();
    }
    private void handleStop(ActionEvent actionEvent){

        simulatorControl.stop();
    }

    private void checkTracked(ActionEvent actionEvent){
        this.statTrack.showTrackingInfo(stage);
    }
    private void colorBest(ActionEvent actionEvent){
        this.mapView.colorBestAnimals(this.statTrack.getBestGenotype());
    }
    private void initializeStats(){
        statisticsTitle = new Text("Statistics of current simulation:");
        statisticsTitle.setStyle("-fx-font-weight: bold");
        dayNumber = new Text("Day: " + this.simulatorControl.getDayNum());
        animalsCount = new Text("Animals alive: " + this.statTrack.getAnimalsCount());
        grassCount = new Text("Grass count: " + this.statTrack.getGrassCount());
        dominatingGenotype = new Text("Dominating genotype: "+ this.statTrack.getBestGenes());
        averageEnergy = new Text("Average energy level: "+ this.statTrack.getAverageEnergy());
        averageLifeSpan = new Text("Average life span: "+this.statTrack.getLifeSpan());
        averageChildrenCount = new Text("Average children count: " + this.statTrack.getChildrenCount());
        this.getChildren().addAll(statisticsTitle,dayNumber,animalsCount,grassCount,dominatingGenotype,averageEnergy,averageLifeSpan,averageChildrenCount);
    }

    public void updateStats(){
        this.dayNumber.setText("Day: " + this.simulatorControl.getDayNum());
        this.animalsCount.setText("Animals alive: " + this.statTrack.getAnimalsCount());
        this.grassCount.setText("Grass count: "+this.statTrack.getGrassCount());
        this.dominatingGenotype.setText("Dominating genotype: "+ this.statTrack.getBestGenes());
        this.averageEnergy.setText("Average energy level: "+ this.statTrack.getAverageEnergy());
        this.averageLifeSpan.setText("Average life span: "+this.statTrack.getLifeSpan());
        this.averageChildrenCount.setText("Average children count: " + this.statTrack.getChildrenCount());
    }

    public void makeNewBoard(ActionEvent actionEvent){
        Stage dialog = new Stage();
        dialog.initOwner(this.stage);
        SimulationStartUp.crateSimulation(dialog);
    }





}
