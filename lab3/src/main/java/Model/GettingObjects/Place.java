package Model.GettingObjects;

import Model.GeoPosition;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {
    private String name;
    private String country;
    private String town;
    private String region;
    private String continent;
    private String street;
    private @JsonProperty("housenumber") String houseNumber;
    private @JsonProperty("countrycode") String countryCode;
    private @JsonProperty("point") GeoPosition position;
}
