package Simulator;

public interface IPositionChangeObserver {

    /**
     * Informs an observer that position of animal had changed
     * @param oldPosition old position vector of a given animal
     * @param newPosition new position vector of a given animal
     * @param animal animal which position had chnaged
     */
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
