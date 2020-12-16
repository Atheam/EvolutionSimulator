package Simulator;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;




public class MapView extends StackPane {

    private Canvas canvas;
    private final int objectSize = 20;
    private GrassMap map;

    public MapView(GrassMap map){
        this.map = map;

        this.canvas = new Canvas(map.getMapSize().x*objectSize,map.getMapSize().y*objectSize);
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

            float R = Math.min(1,animal.getEnergy()/SimulationStartUp.START_ENERGY);

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


}
