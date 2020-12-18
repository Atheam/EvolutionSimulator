package Simulator;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;


public class SimulatorControl {
    private Timeline timeline;
    private IEngine engine;
    private MapView mapView;
    private StatPanel statPanel;
    private int dayNum = 0;

    public SimulatorControl(IEngine engine,MapView mapView,Config config){

        this.mapView = mapView;
        this.engine = engine;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(config.getRefreshSpeed()), this::step));
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void setStatPanel(StatPanel statPanel){
        this.statPanel = statPanel;
    }
    public int getDayNum(){
        return dayNum;
    }

    private void step(ActionEvent actionEvent){
        this.engine.run();
        this.mapView.draw();
        this.dayNum+=1;
        this.statPanel.updateStats();
    }
    public void start(){
        this.timeline.play();
    }
    public void stop(){
        this.timeline.stop();
    }

}
