package model.gettingobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDescription {
    private String xid;
    private String name;
    private Info info;
    private @JsonProperty("wikipedia_extracts")
    WikiInfo wikiInfo;

    @Getter
    @Setter
    public static final class WikiInfo {
        private String title;
        private String text;
    }

    @Getter
    @Setter
    public static final class Info {
        private String descr;
    }
}
