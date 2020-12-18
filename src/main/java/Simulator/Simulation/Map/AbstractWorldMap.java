package Simulator;


import java.util.*;

abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver{

    protected Map<Vector2d,List<Animal>> animals = new HashMap<>();
    protected Vector2d mapSize;


    public Map<Vector2d,List<Animal>> getAnimals(){
        return this.animals;
    }

    @Override
    public Vector2d getMapSize() {
        return mapSize;
    }

    /**
     * Checks if the position is occupied by any element
     *
     * @param position position vector to be checked
     * @return true if position is occupied
     *         false if position is not occupied
     */

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.objectsAt(position) != null;
    }

    /**
     * Transforms a given position vector into
     * an actual position on the Map
     * @param position position vector which is transformed
     * @return an actual position vector on the Map
     */

    public abstract Vector2d mapPosition(Vector2d position);


    /**
     * Get a list of all alive animals
     * The list is just values of the Map <Vector2d, List<Animal>>
     * But all of the animals are in 1D instead of List
     * @return List of all alive animals
     */

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

    /**
     * Check if animal can move to a given position
     * @param position position vector which is checked
     * @return true if animal can move to a position
     *         false if animal cannot move to a position
     */

    @Override
    public abstract boolean canMoveTo(Vector2d position);

    /**
     * Adds an animal at a given position
     * @param animal animal to be added
     * @param position position vector at which animal is added
     */

    private void animalAdd(Animal animal, Vector2d position){
        if(!animals.containsKey(position)) animals.put(position,new ArrayList<>());
        animals.get(position).add(animal);
    }

    /**
     * Handles performing all of the actions
     * that are done at every day cycle
     */
    @Override
    public abstract void mapCycle();

    /**
     * Places a given animal on the map
     *
     * @param animal animal to be placed
     */
    @Override
    public void place(Animal animal) {
        animalAdd(animal,animal.getPosition());
        animal.addObserver(this);
    }

    /**
     * Gets an object or a list of elements at a given position
     * Animals are prioritized over other elements
     * @param position position vector which from the object is fetched
     * @return an object at a given position
     */

    @Override
    public Object objectsAt(Vector2d position){
        return animals.get(position);
    }

    /**
     * Check is if the object is occupied by an Animal
     *
     * @param position position vector to be checked
     * @return true if the position is occupied by an Animal
     *         false if the position is not occupied by an Animal
     */

    private boolean occupiedByAnimal(Vector2d position){
        Object obj = objectsAt(position);
        return !(obj instanceof Grass) && obj != null;
    }

    /**
     * Removes an animal from a given position on the map
     * @param animal animal which is removed
     * @param position position vector to remove animal from
     */

    public void removeAnimal(Animal animal,Vector2d position){
        if(this.animals.containsKey(position)){
           this.animals.get(position).remove(animal);
           if(this.animals.get(position).isEmpty()) this.animals.remove(position);
        }
    }

    /**
     * Informs observer that a position of given animal had changed
     * @param oldPosition old position vector of an animal
     * @param newPosition new position vector of an animal
     * @param animal animal which position had changed
     */

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition,Animal animal) {
        removeAnimal(animal,oldPosition);
        animalAdd(animal,newPosition);
    }

    /**
     * Updated energy of all animals on the map
     */

    @Override
    public abstract void updateEnergy();

    /**
     * Gets a list of animals with the highest amount
     * of energy at a given position
     * @param position position vector on the map
     * @return list of animals with the highest amount of energy
     */
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

    /**
     * Gets animals with the second highest amount of energy
     * @param position position vector on the map
     * @return list of animals with the second highest amount of energy
     */
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

    /**
     * Gets a list of positions occupied by more than one animal
     * @return list of position vectors with multiple animals on them
     */

    @Override
    public List<Vector2d> getMultipleAnimalsPositions(){
        List<Vector2d> multipleAnimalsPositions = new ArrayList<>();
        for(List<Animal> a: this.animals.values()){
            if(a.size() >= 2) multipleAnimalsPositions.add(a.get(0).getPosition());
        }
        return multipleAnimalsPositions;
    }

    /**
     * Gets a random not occupied by animal position on the map
     * out of the 8 positions around the given position
     *
     * @param position position vector around which the free place is found
     * @return a random position vector around the given position
     *         if no position is free random position is chosen
     */

    @Override
    public Vector2d getFreePlace(Vector2d position){
        Random randomizer = new Random();
        int n = randomizer.nextInt(8);
        MapDirection direction = MapDirection.values()[n];
        Vector2d unitVector = direction.toUnitVector();
        for(int i = 0; i < 8;i++){
            Vector2d newPos = this.mapPosition(position.add(unitVector));
            if(!this.occupiedByAnimal(newPos)) return newPos;
            else{
                direction = direction.next();
            }
        }
        return this.mapPosition(position.add(unitVector));

    }

}
