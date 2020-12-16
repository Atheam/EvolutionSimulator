package Simulator;




public class World {


    public static void main(String[] args) {

        javafx.application.Application.launch(SimulationStartUp.class);
    }

    /* public static void main(String[] args) { Vector2d mapSize = new Vector2d(10,10);
        Vector2d jungleSize = new Vector2d(5,4);
        Vector2d junglePosition = new Vector2d(mapSize.x/4,mapSize.y/4);
        float energyCost = 1;
        float grassEnergy = 15;
        float startEnergy = 10;
        int n = 10;
        Vector2d[] startPositions = new Vector2d[n];
        Random randomizer = new Random();
        for(int i = 0; i < n;i++){
            int x = randomizer.nextInt(mapSize.x);
            int y = randomizer.nextInt(mapSize.y);
            Vector2d v = new Vector2d(x,y);
            startPositions[i] = v;
        }



        IWorldMap map = new GrassMap(mapSize,junglePosition,jungleSize,grassEnergy);
        SimulationEngine engine = new SimulationEngine(map,startPositions,energyCost,startEnergy);
        engine.run();
        }*/
}
