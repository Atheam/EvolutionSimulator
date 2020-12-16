package Simulator;


import java.util.*;

abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver{

    protected Map<Vector2d,List<Animal>> animals = new HashMap<>();
    protected Vector2d mapSize;

    @Override
    public Vector2d getMapSize() {
        return mapSize;
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectsAt(position) != null;
    }

    public abstract Vector2d mapPosition(Vector2d position);

    @Override
    public ArrayList<Animal> getAnimalsList(){
        ArrayList<Animal> list = new ArrayList<>();
        for(List<Animal> animals : animals.values()){
            for(Animal animal: animals){
                list.add(animal);
            }
        }
        return list;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    private void animalAdd(Animal animal, Vector2d position){
        if(!animals.containsKey(position)) animals.put(position,new ArrayList<>());
        animals.get(position).add(animal);
    }

    @Override
    public abstract void mapCycle();

    @Override
    public void place(Animal animal) {
        animalAdd(animal,animal.getPosition());
        animal.addObserver(this);
    }

    @Override
    public Object objectsAt(Vector2d position){
        return animals.get(position);
    }

    private boolean occupiedByAnimal(Vector2d position){
        Object obj = objectsAt(position);
        return !(obj instanceof Grass) && obj != null;
    }

    public void removeAnimal(Animal animal,Vector2d position){
        if(this.animals.containsKey(position)){
           this.animals.get(position).remove(animal);
           if(this.animals.get(position).isEmpty()) this.animals.remove(position);
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition,Animal animal) {
        removeAnimal(animal,oldPosition);
        animalAdd(animal,newPosition);
    }

    @Override
    public abstract void updateEnergy();

    @Override
    public List<Animal> getStrongestAnimals(Vector2d position){
        List<Animal> animals = this.animals.get(position);
        float mostEnergy = animals.stream().max(Comparator.comparing(Animal::getEnergy)).get().getEnergy();

        List<Animal> strongestAnimals = new ArrayList<>();
        for(Animal animal : animals){
            if(animal.getEnergy() == mostEnergy) strongestAnimals.add(animal);
        }
        return strongestAnimals;
    }

    @Override
    public List<Animal> getSecondStrongestAnimals(Vector2d position){

        List<Animal> animals = this.animals.get(position);
        float mostEnergy = animals.stream().max(Comparator.comparing(Animal::getEnergy)).get().getEnergy();
        float secondMostEnergy = Float.MIN_VALUE;
        for(Animal a: animals){
            float energy = a.getEnergy();
            if(energy != mostEnergy && energy > secondMostEnergy){
                secondMostEnergy = energy;
            }
        }
        List<Animal> secondStrongestAnimals = new ArrayList<>();
        for(Animal animal: animals){
            if(animal.getEnergy() == secondMostEnergy) secondStrongestAnimals.add(animal);
        }
        return secondStrongestAnimals;

    }

    @Override
    public List<Vector2d> getMultipleAnimalsPositions(){
        List<Vector2d> multipleAnimalsPositions = new ArrayList<>();
        for(List<Animal> a: this.animals.values()){
            if(a.size() >= 2) multipleAnimalsPositions.add(a.get(0).getPosition());
        }
        return multipleAnimalsPositions;
    }

    @Override
    public Vector2d getFreePlace(Vector2d v){
        Random randomizer = new Random();
        int n = randomizer.nextInt(8);
        MapDirection direction = MapDirection.values()[n];
        Vector2d unitVector = direction.toUnitVector();
        for(int i = 0; i < 8;i++){
            Vector2d newPos = this.mapPosition(v.add(unitVector));
            if(!this.occupiedByAnimal(newPos)) return newPos;
            else{
                direction = direction.next();
            }
        }
        return this.mapPosition(v.add(unitVector));

    }

}
