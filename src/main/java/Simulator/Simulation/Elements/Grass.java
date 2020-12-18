package Simulator;

public class Grass {
    private final Vector2d position;
    private final float energy;

    public float getEnergy() {
        return energy;
    }

    public Grass(Vector2d v,float energy){
        this.energy = energy;
        this.position = v;
    }


}
