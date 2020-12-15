package Simulator;

import java.util.*;



public class GrassMap extends AbstractWorldMap{

    private final Vector2d jungleSize;
    private final Vector2d junglePosition;
    private final float grassEnergy;


    private final Map<Vector2d,Grass> grasses = new LinkedHashMap<>();


    public GrassMap(Vector2d mapSize, Vector2d junglePosition, Vector2d jungleSize,float grassEnergy){
        this.mapSize = mapSize;
        this.jungleSize = jungleSize;
        this.junglePosition = junglePosition;
        this.grassEnergy = grassEnergy;
    }

    @Override
    public Vector2d mapPosition(Vector2d position){
        int x = Math.floorMod(position.x,mapSize.x);
        int y = Math.floorMod(position.y,mapSize.y);
        return new Vector2d(x,y);
    }

    @Override
    public void mapCycle() {
        growGrassInJungle();
        growGrassInSteppe();
    }



    private void growGrassInJungle(){
        Random randomizer = new Random();
        int x = randomizer.nextInt(jungleSize.x) + junglePosition.x;
        int y = randomizer.nextInt(jungleSize.y) + junglePosition.y;
        Vector2d potentialPosition = new Vector2d(x,y);
        int count = 0;
        while(isOccupied(potentialPosition)){
            if(count == jungleSize.x*jungleSize.y) return;
            count +=1;
            x += 1;
            if(x  == junglePosition.x+jungleSize.x){
                x = junglePosition.x;
                y +=1;
                if(y == junglePosition.y + jungleSize.y) y = junglePosition.y;

            }
            potentialPosition = new Vector2d(x,y);
        }
        grasses.put(potentialPosition,new Grass(potentialPosition,this.grassEnergy));
    }


    private void growGrassInSteppe(){
        Random randomizer = new Random();
        int x = randomizer.nextInt(jungleSize.x) + junglePosition.x;
        int y = randomizer.nextInt(jungleSize.y) + junglePosition.y;
        Vector2d potentialPosition = new Vector2d(x,y);
        int count = 0;
        while(isInJungle(potentialPosition) || isOccupied(potentialPosition)){
            if(count == mapSize.x * mapSize.y - jungleSize.x - jungleSize.y) return;
            count +=1;
            x+=1;
            if(x == mapSize.x){
                x =0;
                y +=1;
                if(y == mapSize.y) y = 0;
            }
            potentialPosition = new Vector2d(x,y);
        }
        grasses.put(potentialPosition,new Grass(potentialPosition,this.grassEnergy));
    }


    public boolean isInJungle(Vector2d position){
        return position.follows(junglePosition)&& position.precedes(junglePosition.add(jungleSize));
    }

    @Override
    public void updateEnergy() {
        List<Vector2d> eatenGrassPositions = new ArrayList<>();
        for(Vector2d grassPosition : grasses.keySet()){

            if(this.animals.containsKey(grassPosition)){

                eatenGrassPositions.add(grassPosition);

                List<Animal> strongestAnimals = this.getStrongestAnimals(grassPosition);

                float addedEnergy = this.grasses.get(grassPosition).getEnergy()/ strongestAnimals.size();
                for(Animal a: strongestAnimals) a.addEnergy(addedEnergy);
            }
        }
        for(Vector2d v: eatenGrassPositions){
            this.grasses.remove(v);
        }

    }


    @Override
    public Object objectsAt(Vector2d position) {
        Object object = super.objectsAt(position);
        if(object != null) return object;
        return grasses.get(position);
    }



}
