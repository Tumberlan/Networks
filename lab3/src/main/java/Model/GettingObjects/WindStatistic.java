package Model.GettingObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class WindStatistic {
    @Getter @Setter
    public static final class Wind {
        private double speed;
        private double gust;
        private @JsonProperty("degree") int degree;
        private @JsonProperty("clouds") int clouds;

        public WindDirection getWindDirection() {
            int index = (this.degree % 360) / 45;
            return WindDirection.values()[index];
        }
    }

    @Getter
    @Setter
    public static final class Clouds {
    }
}
