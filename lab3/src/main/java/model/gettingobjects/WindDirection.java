package model.gettingobjects;

public enum WindDirection {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    NORTH_WEST,
    NORTH_EAST,
    SOUTH_WEST,
    SOUTH_EAST;

    public String convertToString() {
        switch (this) {
            case NORTH -> {
                return "North";
            }
            case WEST -> {
                return "West";
            }
            case SOUTH -> {
                return "South";
            }
            case EAST -> {
                return "East";
            }
            case NORTH_WEST -> {
                return "North-West";
            }
            case NORTH_EAST -> {
                return "North-East";
            }
            case SOUTH_WEST -> {
                return "South-West";
            }
            case SOUTH_EAST -> {
                return "South-East";
            }
        }
        return null;
    }
}
