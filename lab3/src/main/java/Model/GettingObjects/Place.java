package Model.GettingObjects;

import Model.GeoPosition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {
    private String name;
    private String country;
    private String town;
    private String region;
    private String continent;
    private String street;
    private @JsonProperty("houseNumber") String houseNumber;
    private @JsonProperty("countryCode") String countryCode;
    private @JsonProperty("position") GeoPosition position;
}
