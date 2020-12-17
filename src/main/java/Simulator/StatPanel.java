package Simulator;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class StatPanel extends VBox {
    private SimulatorControl simulatorControl;
    private StatTrack statTrack;
    private Text animalsCount;
    private Text grassCount;
    private Text dominatingGenotype;
    private Text averageEnergy;
    private Text averageLifeSpan;
    private Text averageChildrenCount;
    private Text dayNumber;

    public StatPanel(SimulatorControl simulatorControl,GrassMap map,StatTrack statTrack) {

        this.statTrack = statTrack;
        this.simulatorControl = simulatorControl;

        Button start = new Button("Start Simulation");
        start.setOnAction(this::handleStart);
        Button stop = new Button("Stop Simulation");
        stop.setOnAction(this::handleStop);
        this.getChildren().addAll(start,stop);
        this.initializeStats();
    }

    private void handleStart(ActionEvent actionEvent){
        simulatorControl.start();
    }
    private void handleStop(ActionEvent actionEvent){
        simulatorControl.stop();
    }

    private void initializeStats(){
        dayNumber = new Text("Day: " + this.simulatorControl.getDayNum());
        animalsCount = new Text("Animals alive: " + this.statTrack.getAnimalsCount());
        grassCount = new Text("Grass count: " + this.statTrack.getGrassCount());
        dominatingGenotype = new Text("Dominating genotype: "+ this.statTrack.getBestGenotype());
        averageEnergy = new Text("Average energy level: "+ this.statTrack.getAverageEnergy());
        averageLifeSpan = new Text("Average life span: "+this.statTrack.getLifeSpan());
        averageChildrenCount = new Text("Average children count: " + this.statTrack.getChildrenCount());
        this.getChildren().addAll(dayNumber,animalsCount,grassCount,dominatingGenotype,averageEnergy,averageLifeSpan,averageChildrenCount);
    }

    public void updateStats(){
        this.dayNumber.setText("Day: " + this.simulatorControl.getDayNum());
        this.animalsCount.setText("Animals alive: " + this.statTrack.getAnimalsCount());
        this.grassCount.setText("Grass count: "+this.statTrack.getGrassCount());
        this.dominatingGenotype.setText("Dominating genotype: "+ this.statTrack.getBestGenotype());
        this.averageEnergy.setText("Average energy level: "+ this.statTrack.getAverageEnergy());
        this.averageLifeSpan.setText("Average life span: "+this.statTrack.getLifeSpan());
        this.averageChildrenCount.setText("Average children count: " + this.statTrack.getChildrenCount());
    }


}
