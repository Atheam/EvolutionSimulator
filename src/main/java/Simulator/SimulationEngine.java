package Simulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class SimulationEngine implements IEngine{
    private final IWorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private final float energyLoss;
    private final float startEnergy;
    private final StatTrack statTrack;
    private int daysCount = 0;

    public SimulationEngine(IWorldMap map,Vector2d[] startPositions,float energyCost,float startEnergy,StatTrack statTrack){
        this.energyLoss = energyCost;
        this.startEnergy = startEnergy;
        this.statTrack = statTrack;
        this.map = map;
        for(Vector2d v: startPositions){
            Animal animal = new Animal(map,v,startEnergy);
            this.animals.add(animal);
            this.statTrack.addGenes(animal.getGenotype());
        }
    }



    private void removeDead(){
        Iterator<Animal> i = this.animals.iterator();
        while(i.hasNext()){
            Animal animal = i.next();
            if(animal.getEnergy() <= 0) {
                this.map.removeAnimal(animal, animal.getPosition());
                this.statTrack.animalDeathUpdate(animal);
                i.remove();
            }
            else animal.addDaysAlive();

        }
    }

    private void animalsMove(){
        for(Animal animal : animals ){
            animal.move(animal.getGenotype().getDirection());
        }
    }

    private void animalsEat(){
        map.updateEnergy();
    }


    private void animalsSpawn(){
        Random randomizer = new Random();

        List<Vector2d> multipleAnimalsPositions = this.map.getMultipleAnimalsPositions();
        for(Vector2d v: multipleAnimalsPositions){

            List<Animal> strongestAnimals = this.map.getStrongestAnimals(v);
            List<Animal> secondStrongestAnimals = this.map.getSecondStrongestAnimals(v);

            Animal firstParent,secondParent;

            if(strongestAnimals.size() == 1){
                firstParent = strongestAnimals.get(0);
                secondParent = secondStrongestAnimals.get(randomizer.nextInt(secondStrongestAnimals.size()));
            }
            else{
                int n = randomizer.nextInt((strongestAnimals.size()));
                firstParent = strongestAnimals.get(n);
                secondParent = strongestAnimals.get((n+1)%strongestAnimals.size());
            }

            float firstParentEnergy = firstParent.getEnergy();
            float secondParentEnergy = secondParent.getEnergy();

            if(secondParentEnergy < startEnergy*0.5) continue;

            Vector2d freePlace = this.map.getFreePlace(v);
            Animal animal = new Animal(this.map,freePlace,firstParent.getGenotype().combineGenes(secondParent.getGenotype()));

            this.animals.add(animal);
            this.statTrack.addGenes(animal.getGenotype());

            animal.setEnergy(firstParentEnergy*0.25f + secondParentEnergy*0.25f);
            firstParent.subtractEnergy(firstParentEnergy*0.25f);
            secondParent.subtractEnergy(secondParentEnergy*0.25f);
            firstParent.addChild(animal);
            secondParent.addChild(animal);


        }


    }

    private void grassSpawn(){
        this.map.mapCycle();
    }
    private void decreaseEnergy(){
        for(Animal animal : this.animals){
            animal.subtractEnergy(energyLoss);
        }
    }

    private void dayCycle(){
        daysCount++;
        this.statTrack.daysUpdate(daysCount);
        decreaseEnergy();
        removeDead();
        animalsSpawn();
        animalsMove();
        animalsEat();
        grassSpawn();

    }

    public void run(){
            this.dayCycle();
    }



}
