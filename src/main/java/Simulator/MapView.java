package Simulator;




import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;


public class MapView extends StackPane {

    private Canvas canvas;
    private final int objectSize;
    private GrassMap map;
    private Config config;
    private StatTrack statTrack;
    private Vector2d clickedPosition;
    private Stage stage;
    public MapView(GrassMap map,Stage stage,Config config,int objectSize){

        this.objectSize = objectSize;
        this.config = config;
        this.map = map;
        this.stage = stage;
        setPrefSize(800,600);
        this.canvas = new Canvas(map.getMapSize().x*objectSize,map.getMapSize().y*objectSize);
        this.canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                int x = (int) event.getX() / objectSize;
                int y = (int) event.getY()/ objectSize;
                Vector2d position =new Vector2d(x,y);
                if(map.getAnimals().get(position) != null) {
                    clickedPosition = position;
                    openDialog();
                }
            }
        });

        this.getChildren().addAll(canvas);
        draw();
    }
    public void draw(){
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setFill(Color.LIGHTGREEN);
        g.fillRect(0,0,this.canvas.getWidth(),this.canvas.getHeight());
        g.setFill(Color.GREEN);
        g.fillRect(this.map.getJunglePosition().x * objectSize, this.map.getJunglePosition().y * objectSize,this.map.getJungleSize().x * objectSize,this.map.getJungleSize().y * objectSize);
        drawAnimals(g);
        drawGrasses(g);

    }
    public void drawAnimals(GraphicsContext g){
        for(Animal animal : this.map.getAnimalsList()){

            float R = Math.min(1,animal.getEnergy()/config.getStartEnergy());

            g.setFill(Color.color(R,0,0));
            g.fillRect(animal.getPosition().x*objectSize,animal.getPosition().y*objectSize,objectSize,objectSize);
        }
    }

    public void drawGrasses(GraphicsContext g){
        for(Vector2d v: this.map.getGrassPositions()){
            g.setFill(Color.color(0.70,0.93,0.30));
            g.fillRect(v.x*objectSize,v.y*objectSize,objectSize,objectSize);
        }
    }
    public void colorBestAnimals(Genotype genotype){
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setFill(Color.BLUE);
        for(Animal animal : this.map.getAnimalsList()){
            if(animal.getGenotype().equals(genotype)) {
                Vector2d pos = animal.getPosition();
                g.fillRect(pos.x * objectSize, pos.y * objectSize, objectSize,objectSize);
            }

        }
    }
    public void setStatTrack(StatTrack statTrack){
        this.statTrack = statTrack;
    }

    public void openDialog(){
        Button info = new Button("Show animal info");
        info.setOnAction(this::showAnimalInfo);
        Stage dialog = new Stage();
        dialog.initModality(Modality.NONE);
        dialog.initOwner(stage);
        dialog.setTitle("Animal actions");
        VBox dialogVBox = new VBox(20);
        Button tracking = new Button("Start tracking this animal");
        dialogVBox.setAlignment(Pos.CENTER);
        tracking.setOnAction(event -> {
            startTracking(event);
            Text text = new Text("Animal is now being tracked");
            dialogVBox.getChildren().add(text);
        });
        dialogVBox.getChildren().addAll(info,tracking);
        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setAlwaysOnTop(true);


    }
    public void startTracking(ActionEvent event){
        this.statTrack.addTracking(clickedPosition);
    }
    public void showAnimalInfo(ActionEvent event){

        Animal animal = this.map.getAnimals().get(clickedPosition).get(0);
        Stage dialog = new Stage();
        dialog.initModality(Modality.NONE);
        dialog.initOwner(stage);
        dialog.setTitle("Animal info");
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text("Genotype of clicked animal : \n"+animal.getGenotype().toString()));
        Scene dialogScene = new Scene(dialogVbox, 350, 200);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setAlwaysOnTop(true);


    }


}
