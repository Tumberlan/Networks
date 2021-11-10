package Model.GettingObjects;

import Model.GeoPosition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDescription {
    private String xid;
    private String name;
    private Info info;
    private @JsonProperty("wikipedia_extracts") WikiInfo wikiInfo;
}