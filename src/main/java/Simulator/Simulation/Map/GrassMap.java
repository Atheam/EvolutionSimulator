package Simulator.Simulation.Map;


import Simulator.Simulation.Elements.Animal;
import Simulator.Simulation.Elements.Grass;
import Simulator.Simulation.Math.Vector2d;

import java.util.*;



public class GrassMap extends AbstractWorldMap{



    public final Vector2d jungleSize;
    public final Vector2d junglePosition;
    private final float grassEnergy;


    private final Map<Vector2d, Grass> grasses = new LinkedHashMap<>();

    public Vector2d getJungleSize() {
        return jungleSize;
    }

    public Vector2d getJunglePosition() {
        return junglePosition;
    }

    public Set<Vector2d> getGrassPositions(){
        return this.grasses.keySet();

    }


    public GrassMap(Vector2d mapSize, Vector2d jungleSize,float grassEnergy){
        this.mapSize = mapSize;
        this.jungleSize = jungleSize;
        int x = (int) Math.round((mapSize.x-jungleSize.x)/2.0) ;
        int y = (int) Math.round((mapSize.y - jungleSize.y)/2.0) ;
        this.junglePosition =  new Vector2d(x,y);
        this.grassEnergy = grassEnergy;

    }

    /**
     * Indicates if animal can move to a given position
     *
     * In case of Grass Map the return value is always true
     * Because the map is continues at the borders and animals
     * can be stacked on each other at a given position
     *
     * @param position position vector which is checked
     * @return true if animal can move to a given position
     *         false if animal cannot move to a given position
     */

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    /**
     * Transforms a given position into actual position on the map
     *
     * @param position position vector which is transformed
     * @return actual position vector on the map
     */

    @Override
    public Vector2d mapPosition(Vector2d position){
        int x = Math.floorMod(position.x,mapSize.x);
        int y = Math.floorMod(position.y,mapSize.y);
        return new Vector2d(x,y);
    }

    /**
     * Handles performing all of the actions
     * that are done at every day cycle
     */

    @Override
    public void mapCycle() {
        growGrassInJungle();
        growGrassInSteppe();
    }


    /**
     * Handles growing one grass elements at a random jungle field
     */

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

    /**
     * Handles growing one grass element at a random steppe field
     */

    private void growGrassInSteppe(){
        Random randomizer = new Random();
        int x = randomizer.nextInt(mapSize.x) ;
        int y = randomizer.nextInt(mapSize.y) ;
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

    /**
     * Checks if a given position lays in the jungle
     * @param position position vector that is checked
     * @return true if a given position is in the jungle
     *         false if a given position is not in the jungle
     */
    public boolean isInJungle(Vector2d position){
        return position.follows(junglePosition)&& position.precedes(junglePosition.add(jungleSize.subtract(new Vector2d(1,1))));
    }

    /**
     * Updated energy of all animals on the map
     */

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

    /**
     * Gets an object or a list of elements at a given position
     * Animals are prioritized over other elements
     * @param position position vector which from the object is fetched
     * @return an object at a given position
     */

    @Override
    public Object objectsAt(Vector2d position) {
        Object object = super.objectsAt(position);
        if(object != null) return object;
        return grasses.get(position);
    }



}
