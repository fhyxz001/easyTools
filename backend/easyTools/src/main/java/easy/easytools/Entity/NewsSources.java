package easy.easytools.Entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum NewsSources {
    HOTACG("0",
            "hotacg",
            "热点ACG",
            NewsParseType.TRADITIONAL.getCode(),
            "entry-title",
            "entry-date",
            "entry-content",
            "yyyyMMd HH:mm,yyyyMdd HH:mm,yyyyMd HH:mm",
            7,null),
    _3DMGAME("1",
            "3dmgame",
            "三大妈",
            NewsParseType.TRADITIONAL.getCode(),
            "bt",
            "time",
            "news_warp_center",
            null,
            6,null),
    VGTIME("2",
            "vgtime",
            "游戏时光",
            NewsParseType.TRADITIONAL.getCode(),
            "art_tit",
            "time_box",
            "topicContent front_content",
            null,
            13,null),
    ALI213("3",
            "ali213",
            "游侠资讯",
            NewsParseType.TRADITIONAL.getCode(),
            "newstit",
            "newstag_l",
            "n_show box-shadow",
            null,
            15,null),
    DMZJ("4",
            "dmzj",
            "动漫之家",
            NewsParseType.TRADITIONAL.getCode(),
            "news_content_head li_img_de autoHeight",
            "data_time",
            "news_content_con",
            null,
            8,null),
    SOHU(
            "5",
            "sohu",
            "搜狐",
            NewsParseType.TRADITIONAL.getCode(),
            "article-title",
            "time-source",
            "article",
            null,
            10,null),
    YOUMIN(
            "6",
            "gamersky",
            "游民星空",
            NewsParseType.TRADITIONAL.getCode(),
            "Mid2L_tit",
            "detail",
            "Mid2L_con",
            null,
            10,null),
    GCORES("7",
            "gcores",
            "机核网",
            NewsParseType.INTERFACE.getCode(),
            "originalPage_title",
            "me-2 u_color-gray-info",
            "DraftEditor-editorContainer",
            null,
            0,"https://www.gcores.com/gapi/v1/articles/"),
    A9VG("8",
            "a9vg",
            "A9VG网",
            NewsParseType.BBS.getCode(),
            "z",
            "authi",
            "t_f",
            null,
            0,null),
    GAO7("99",
            "gao7",
            "搞趣网",
            "0",
            "article-title",
            "article-meta",
            "area-gao7-article",
            null,
            4,null);

    //编号序号
    private String code;
    //网站名称
    private String name;
    //网站中文名
    private String cnName;
    //网站类型
    private String newsParseType;
    //标题所在的标签
    private String titleSelector;
    //时间所在的标签
    private String dateSelector;
    //内容所在的标签
    private String contentSelector;
    //时间格式化格式，null的话为标准格式
    private String dateFormat;
    //需要删除的后缀字符串长度
    private int suffixLength;
    //新闻获取接口地址，用于新闻解析类型为接口渲染时使用
    private String newsApiUrl;


    NewsSources(String code,
                String name,
                String chName,
                String newsParseType,
                String titleSelector,
                String dateSelector,
                String contentSelector,
                String dateFormat,
                int suffixLength,String newsApiUrl) {
        this.code = code;
        this.name = name;
        this.cnName = chName;
        this.newsParseType = newsParseType;
        this.titleSelector = titleSelector;
        this.dateSelector = dateSelector;
        this.contentSelector = contentSelector;
        this.dateFormat = dateFormat;
        this.suffixLength = suffixLength;
        this.newsApiUrl = newsApiUrl;
    }

    public static NewsSources getNewsSourcesByCode(String code) {
        for (NewsSources newsSources : NewsSources.values()) {
            if (newsSources.getCode().equals(code)) {
                return newsSources;
            }
        }
        return null;
    }
    //getCodeByName
    public static NewsSources getSiteTypeByName(String name) {
        for (NewsSources siteType : NewsSources.values()) {
            if (siteType.getName().equals(name)) {
                return siteType;
            }
        }
        return null;
    }
    //返回一个Name的List
    public static List<String> getNameList(){
        List<String> nameList = new ArrayList<>();
        for (NewsSources siteType : NewsSources.values()) {
            nameList.add(siteType.getName());
        }
        return nameList;
    }
}
