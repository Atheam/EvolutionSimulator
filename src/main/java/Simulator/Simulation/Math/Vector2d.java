package Simulator.Simulation.Math;


import java.util.Objects;


public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){

        return "(" + (this.x) + "," + (this.y) + ")";
    }
    public boolean precedes(Vector2d other){

        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){

        return this.x >= other.x && this.y >= other.y;
    }


    public Vector2d add(Vector2d other){
        int x = this.x + other.x;
        int y = this.y + other.y;

        return new Vector2d(x,y);
    }

    public Vector2d subtract(Vector2d other){
        int x = this.x - other.x;
        int y = this.y - other.y;
        return new Vector2d(x,y);
    }

    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof Vector2d)) return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite(){
        int x = this.x * (-1);
        int y = this.y * (-1);
        return new Vector2d(x,y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
















}
