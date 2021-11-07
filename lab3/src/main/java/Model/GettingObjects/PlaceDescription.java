package Model.GettingObjects;

import Model.GeoPosition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDescription {
    private String name;
    private String xid;
    private @JsonProperty("info") String information;
    private @JsonProperty("position") GeoPosition geoPosition;
    private @JsonProperty("wikiInfo") WikiInfo wikiInfo;
}
