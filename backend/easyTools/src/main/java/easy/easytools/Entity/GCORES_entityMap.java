package easy.easytools.Entity;

import lombok.Data;

@Data
public class GCORES_entityMap {
    //EMBED-嵌入式视频类型，IMAGE-图片类型
    String type;
    String mutability;
    GCORES_entityMap_data data;
}
