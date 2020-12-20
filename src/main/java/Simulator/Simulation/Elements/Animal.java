package Simulator.Simulation.Elements;


import Simulator.Simulation.Logic.Genotype;
import Simulator.Simulation.Map.IPositionChangeObserver;
import Simulator.Simulation.Map.IWorldMap;
import Simulator.Simulation.Map.MapDirection;
import Simulator.Simulation.Math.Vector2d;

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
    private int daysAlive =1;
    private int deathDay = 0;
    private List<Animal> children = new ArrayList<>();

    public float getEnergy(){
        return this.energy;
    }

    public void setDeathDay(int day){
        this.deathDay = day;
    }
    public int getDeathDay(){
        return this.deathDay;
    }
    public List<Animal> getChildren(){
        return this.children;
    }
    public int getDaysAlive(){
        return this.daysAlive;
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

    /**
     * Adds an animal to a list of children
     * @param child animal to added
     */

    public void addChild(Animal child){
        this.children.add(child);
    }

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

    /**
     * Increases the counter of days this animal is alive
     */
    public void addDaysAlive(){
        this.daysAlive+=1;
    }

    /**
     * Adds observer of this animal to list of observers
     * @param observer observer to be added
     */

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    /**
     * Informs all the observers that a position of this animal had changed
     * @param oldPosition old position vector of animal
     * @param newPosition new position vector of animal
     */
    void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition,newPosition,this);
        }
    }

    /**
     * Handles animal move action
     * @param direction direction at which animal moves
     */

    public void move(MapDirection direction) {
        this.direction = direction;
        Vector2d newPosition = this.map.mapPosition(this.position.add(this.direction.toUnitVector()));
        if(this.map.canMoveTo(position)) {
            this.positionChanged(this.position,newPosition);
            this.position = newPosition;
        }
    }

    /**
     * Increases this animal energy level by a given amount
     * @param addedEnergy energy to be added
     */
    public void addEnergy(float addedEnergy) {
        this.energy += addedEnergy;
    }

    /**
     * Decreses this animal energy level by a given amount
     * @param minusEnergy energy to be subtracted
     */
    public void subtractEnergy(float minusEnergy){
        this.energy -= minusEnergy;
    }

}
