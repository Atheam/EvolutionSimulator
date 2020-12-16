package Simulator;

public enum MapDirection {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

    public String toString() {
        switch(this) {
            case NORTH:
                return "North";
            case NORTHEAST:
                return "North East";
            case EAST:
                return "East";
            case SOUTHEAST:
                return "South East";
            case SOUTH:
                return "South";
            case SOUTHWEST:
                return "South West";
            case WEST:
                return "West";
            case NORTHWEST:
                return "North West";
            default:
                return null;

        }
    }

    public MapDirection next(){
       return MapDirection.values()[(this.ordinal()+1)%values().length];
    }

    public Vector2d toUnitVector(){
        switch(this){
            case NORTH:
                return new Vector2d(0,1);
            case NORTHEAST:
                return new Vector2d(1,1);
            case EAST:
                return new Vector2d(1,0);
            case SOUTHEAST:
                return new Vector2d(1,-1);
            case SOUTH:
                return new Vector2d(0,-1);
            case SOUTHWEST:
                return new Vector2d(-1,-1);
            case WEST:
                return new Vector2d(-1,0);
            case NORTHWEST:
                return new Vector2d(-1,1);
            default:
                return new Vector2d(0,0);
        }
    }

}
