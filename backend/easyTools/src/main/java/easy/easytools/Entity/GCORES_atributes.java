package easy.easytools.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GCORES_atributes {
    String title;
    String content;
    @JsonProperty("published-at")
    String published_at;
}
