package Simulator;

import java.util.List;


public interface IWorldMap extends IPositionChangeObserver{

    Vector2d getMapSize();
    boolean canMoveTo(Vector2d position);
    void updateEnergy();
    void removeAnimal(Animal animal, Vector2d position);
    void place(Animal animal);
    boolean isOccupied(Vector2d position);
    void mapCycle();
    Vector2d mapPosition(Vector2d position);
    Object objectsAt(Vector2d position);
    Vector2d getFreePlace(Vector2d v);
    List<Vector2d> getMultipleAnimalsPositions();
    List<Animal> getStrongestAnimals(Vector2d position);
    List<Animal> getSecondStrongestAnimals(Vector2d position);


}
