package model.gettingobjects;

import model.GeoPosition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {
    private String name;
    private String country;
    private String city;
    private String state;
    private String street;
    private @JsonProperty("housenumber")
    String houseNumber;
    private @JsonProperty("countrycode")
    String countryCode;
    private @JsonProperty("point")
    GeoPosition position;
}
