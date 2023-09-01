package easy.easytools.Entity.NewsFolder;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum NewsParseType {
    TRADITIONAL("0","传统渲染"),
    INTERFACE("1","接口渲染"),
    BBS("2","论坛渲染");

    private String code;
    private String desc;

    NewsParseType(String code,
                String desc) {
        this.code = code;
        this.desc = desc;
    }

}
