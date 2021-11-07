package Model.GettingObjects;

public enum WindDirection {
    NORTH("North"),
    NORTH_EAST("North-East"),
    EAST("East"),
    SOUTH_EAST("South-East"),
    SOUTH("South"),
    SOUTH_WEST("South-West"),
    WEST("West"),
    NORTH_WEST("North-West");

    private final String text;

    WindDirection(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
