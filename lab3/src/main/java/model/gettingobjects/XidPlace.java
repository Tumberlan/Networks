package model.gettingobjects;

import model.GeoPosition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XidPlace {
    private String xid;
    private String name;
    private String kinds;
    private @JsonProperty("point")
    GeoPosition position;
}
