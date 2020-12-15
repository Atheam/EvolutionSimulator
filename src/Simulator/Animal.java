package Simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal {
    private final Random randomizer = new Random();

    private MapDirection direction = MapDirection.values()[randomizer.nextInt(8)];
    private Vector2d position;
    private final IWorldMap map;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private float energy;
    private final Genotype genotype;

    public float getEnergy(){ return this.energy;}



    public Animal(IWorldMap map, Vector2d position,Genotype genotype) {
        this.map = map;
        this.position = position;
        this.genotype = genotype;
        map.place(this);
    }

    public Animal(IWorldMap map, Vector2d position,float startEnergy) {
        this.energy = startEnergy;
        this.map = map;
        this.position = position;
        this.genotype = new Genotype(32);
        map.place(this);
    }


    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public Genotype getGenotype(){
        return this.genotype;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public String toString() {
        switch (direction) {
            case NORTH:
                return "N";
            case WEST:
                return "W";
            case SOUTH:
                return "S";
            case EAST:
                return "E";
            case NORTHEAST:
                return "NE";
            case NORTHWEST:
                return "NW";
            case SOUTHEAST:
                return "SE";
            case SOUTHWEST:
                return "SW";
            default:
                return null;
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }


    void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition,newPosition,this);
        }
    }

    public void move(MapDirection direction) {
        this.direction = direction;
        Vector2d newPosition = this.map.mapPosition(this.position.add(this.direction.toUnitVector()));
        if(this.map.canMoveTo(position)) {
            this.positionChanged(this.position,newPosition);
            this.position = newPosition;
        }
    }
    public void addEnergy(float addedEnergy) {
        this.energy += addedEnergy;
    }
    public void subtractEnergy(float minusEnergy){
        this.energy -= minusEnergy;
    }

}
