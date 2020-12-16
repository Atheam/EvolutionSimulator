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

    public SimulatorControl(IEngine engine,MapView mapView){
        this.mapView = mapView;
        this.engine = engine;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(100), this::step));
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    private void step(ActionEvent actionEvent){
        this.engine.run();
        this.mapView.draw();
    }
    private void start(){
        this.timeline.play();
    }
    private void stop(){
        this.timeline.stop();
    }

}
