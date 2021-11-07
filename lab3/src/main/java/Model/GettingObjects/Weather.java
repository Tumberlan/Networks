package Model.GettingObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {
    private @JsonProperty("clouds") int clouds;
    private WindStatistic windStatistic;
    private int visibility;
}
