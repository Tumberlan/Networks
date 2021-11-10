package Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
public class GeoPosition {
    private @JsonAlias("lat")
    double lat;
    private @JsonAlias({"lng", "lon"})
    double lon;
}