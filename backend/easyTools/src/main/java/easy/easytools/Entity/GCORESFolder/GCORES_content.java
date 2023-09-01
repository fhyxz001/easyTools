package easy.easytools.Entity.GCORESFolder;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GCORES_content {
    List<GCORES_blocks> blocks;
    Map<String,GCORES_entityMap> entityMap;
}
