package easy.easytools.Service;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import easy.easytools.Entity.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BBSToolsServiceImpl implements BBSToolsService {
    @Autowired
    ObjectMapper objectMapper;

    String date = "";
    String title = "";
    String newTitle = "";
    String content = "";

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    ApiLogsService apiLogsService;
    @Override
    public News getNewsByUrl(String url) {
        LocalDateTime startTime = LocalDateTime.now();
        //获取调用接口人的ip地址
        String ip = httpServletRequest.getRemoteAddr();
        log.info("调用接口人ip地址为：{}",ip);
        ApiLogs apiLogs = new ApiLogs();
        apiLogs.setCallerIp(ip);
        apiLogs.setInterfaceName("bbs/getNewsByUrl");
        apiLogs.setCallTime(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        apiLogs.setRequestParams(url);
        News news = new News();
        //开始处理数据
        try{
            Document doc = this.getDocument(url);
            String code = this.SiteCheck(url);
            if(code!=null){
                news.setSiteType(code);
            }
            NewsSources newsSources = NewsSources.getNewsSourcesByCode(news.getSiteType());
            switch (newsSources.getNewsParseType()){
                case "0":
                    traditionalWebHandle(newsSources, doc, url, news);
                    break;
                case "1":
                    interfaceWebHandle(newsSources,url,news);
                    break;
                case "2":
                    bbsWebHandle(newsSources,doc,news,url);
                    break;
                default:
                    throw new IllegalArgumentException("网站类型不合法");
            }
        }catch (Exception e){
            apiLogs.setResponseData("-1");
            apiLogs.setExceptionMessage(e.getMessage());
        }
        apiLogs.setResponseData(JSONUtil.toJsonStr(news));
        //获取执行时间
        LocalDateTime endTime = LocalDateTime.now();
        apiLogs.setExecutionTime(endTime.minusNanos(startTime.getNano()).getNano());
        apiLogs.setCreatedAt(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        apiLogsService.saveApiLogs(apiLogs);
        return news;
    }

    private void bbsWebHandle(NewsSources newsSources, Document doc, News news,String url) {
        StringBuilder title = new StringBuilder();
        StringBuilder content = new StringBuilder();
        content.append("文章来源：").append(url).append("<br>");
        switch (newsSources.getCode()){
            case "8":
                Elements allElements = doc.body().getAllElements();
                if(allElements.size()>0){
                    //获取发帖日期数据
                    Element selectElement = allElements.get(0);
                    //获取发帖日期数据
                    Elements dateElements = selectElement.getElementsByTag("em");
                    for (Element element : dateElements) {
                        if(element.text().contains("发表于")){
                            date = element.text().replace("发表于","");
                            break;
                        }
                    }
                    //使用正则替换掉date中的中文字符
                    date = date.replaceAll("[\u4e00-\u9fa5]","");
                    //替换掉date中的·字符
                    date = date.replace("·","");
                    //格式化date
                    DateTime dateTime = DateUtil.parse(date, "yyyy-MM-dd HH:mm");
                    //dateTime转为yyMMdd
                    date = DateUtil.format(dateTime, "yyMMdd");
                    title.append("["+date+"]");

                    Element titleElement = null;
                    for (Element a : allElements) {
                        if("plc ptm pbn vwthd".equals(a.className())){
                            titleElement = a;
                            break;
                        }
                    }
                    //获取title数据
                    Elements titleElements = titleElement.getElementsByTag("a");
                    title.append(titleElements.get(titleElements.size()-2).text());
                    Element contentElement = null;
                    for (Element item : allElements) {
                        if(item.id().contains("postmessage_")){
//                            第一个取到的就是需要解析处理的新闻内容，直接跳出循环，进行后续处理
                            contentElement = item;
                            break;
                        }
                    }
                    news.setTitle(title.toString());
                    //获取content数据
                    Elements t_f = contentElement.getElementsByClass("t_f");
                    for (Element element : t_f) {
                        Elements text = element.getElementsByTag("td");
                        if(text.size()>0){
                            Element td = text.get(0);
                            Element finalWaitHandleData = td.getAllElements().get(0);
                            for (Element item : finalWaitHandleData.getAllElements()) {
                                if (item.getElementsByTag("td")!=null) {
                                    //正则表达式，用于获取以https://file1.a9vg.com/开头，以.jpg|.png|.gif|.jpeg|.bmp结尾的字符串
                                    String regex = "(https://file1.a9vg.com/)(.*?)(.jpg|.png|.gif|.jpeg|.bmp)";
                                    String allText = item.html();
                                    allText = allText.replaceAll(regex, "[img]$0[/img]");
                                    //然后再从text1中找到所有以[/img]开头的内容，以空字符串替换掉从该字符开始直到 上传 之间的所有内容，注意：不包括[/img],[/img]需要保留
                                    String regex2 = "\\[/img\\](.*?)(上传)";
                                    allText = allText.replaceAll(regex2, "[/img]");
                                    //遍历allText字符串，如果遇到<div align="center">，则将其后面的内容全部删除，直到遇到zoomfile="为止
                                    String firstBlockPattern = "<div align=\"center\">[\\s\\S]*?zoomfile=\"";
                                    allText = allText.replaceAll(firstBlockPattern, "");
                                    String secondBlockPattern = "file=[\\s\\S]*?<\\/ignore_js_op>[\\s\\S]*?<\\/div>";
                                    allText = allText.replaceAll(secondBlockPattern, "");
                                    content.append(allText);
                                    break;
                                }
                            }
                        }
                    }
                }

                break;
            default:
                throw new IllegalArgumentException("不合法的解析类型。");
        }
        news.setContent(content.toString());
    }

    private void interfaceWebHandle(NewsSources newsSources, String url, News news) {
        switch (newsSources.getCode()){
            case "7":
                //机核网解析逻辑
                String newsApiUrl = NewsSources.GCORES.getNewsApiUrl();
                //获取url最后一个/后面的字符串，例如https://www.gcores.com/articles/166053的166053
                String newsId = url.substring(url.lastIndexOf("/")+1);
                newsApiUrl = newsApiUrl+newsId;
                String res = HttpUtil.get(newsApiUrl);
                try {
                    GCORES gcores = objectMapper.readValue(res, GCORES.class);
                    GCORES_atributes attributes = gcores.getData().getAttributes();
                    String published_at = attributes.getPublished_at();
                    DateTime dateTime = DateUtil.parse(published_at);
                    //处理时间，将其转换为[yyMMdd]格式
                    String date = "["+DateUtil.format(dateTime, "yyMMdd")+"]";
                    String title = date+attributes.getTitle();
                    news.setTitle(title);

                    StringBuilder content = new StringBuilder();
                    content.append("文章来源："+url+"\n");
                    //处理content
                    GCORES_content gcores_content = objectMapper.readValue(attributes.getContent(), GCORES_content.class);
                    List<GCORES_blocks> blocks = gcores_content.getBlocks();
                    Map<String, GCORES_entityMap> entityMap = gcores_content.getEntityMap();
                    Integer refIndex = 0;
                    for (GCORES_blocks block : blocks) {
                        //unstyled-文本，atomic-需要从entityMap取出的图像文本类型
                        if("atomic".equals(block.getType())){
                            GCORES_entityMap gcores_entityMap = entityMap.get(refIndex.toString());
                            if(gcores_entityMap!=null){
                                String mapType = gcores_entityMap.getType();
                                if("IMAGE".equals(mapType)) {
                                    content.append("[img]");
                                    content.append("https://image.gcores.com/" + gcores_entityMap.getData().getPath());
                                    content.append("[/img]");
                                }
                                content.append("\n");
                            }
                            refIndex++;
                        }else {
                            content.append(block.getText());
                            content.append("\n");
                        }
                    }
                    news.setContent(content.toString());
                } catch (JsonProcessingException e) {
                    throw new IllegalArgumentException("json解析错误,{}",e);
                }
                break;
            default:
                throw new IllegalArgumentException("code类型错误,不支持的接口类型解析");
        }
    }

    private void traditionalWebHandle(NewsSources newsSources, Document doc, String url, News news) {
        //遍历allElements，找到其中有<div class="entry-content">的子元素，保留文本的格式，并且将其中的img标签的数据替换成以[img]开头，以[/img]结尾的字符串
        //找到其中有entry-date的第一条数据，将其格式转换为[yyMMdd],赋予给data字符串
        //找到其中有entry-title的参数，将其赋予给title字符串
        //拼接data和title，赋予给newTitle字符串
        Elements allElements = doc.body().getAllElements();
        boolean firstDate = true;
        for (int i = 0; i < allElements.size(); i++) {
            if (allElements.get(i).className().equals(newsSources.getContentSelector())) {
                content = this.getContent(allElements, i);
            }
            if (allElements.get(i).className().equals(newsSources.getDateSelector()) && firstDate) {
                DateTime parse = this.getDateTime(newsSources, allElements, i);

                Calendar ca = Calendar.getInstance();
                ca.setTime(parse);
                int year = ca.get(Calendar.YEAR);
                int month = ca.get(Calendar.MONTH) + 1;
                int day = ca.get(Calendar.DATE);

                //检查月和日是否为一位数，如果是，则在前面补0
                String monthStr = month < 10 ? "0" + month : String.valueOf(month);
                String dayStr = day < 10 ? "0" + day : String.valueOf(day);
                date = year + monthStr + dayStr;
                //删除date前两个字符
                date = date.substring(2);
                //用[]包裹
                date = "[" + date + "]";
                firstDate = false;
            }
        }
        title = this.getTitle(doc, newsSources);
        newTitle = date + title;
        //在content的开头添加url，作为文章的来源
        content = "[b]文章来源：[/b]" + url + " " + "\n" + content;
        //保留content中的p标签和img标签
        content = content.replaceAll("<(?!p|img|/p|/img).*?>", "");
        //将img标签的src的值替换成以[img]开头，以[/img]结尾的字符串
        content = content.replaceAll("<img.*?src=\"(.*?)\".*?>", "[img]$1[/img]");
        //删除其中所有除了[img]和[/img]的标签
        content = content.replaceAll("<.*?>", "");

        //判断newsSources的name是不是ali213
        if(newsSources !=null){
            if(newsSources.getName()!=null){
                if(newsSources.getName().equals("ali213")){
                    //如果是ali213，则需要进行额外处理，如果出现了 var ，那么去除掉var之后所有的内容
                    if(content.contains("var")){
                        content = content.substring(0,content.indexOf("var"));
                    }
                }
            }
        }
        news.setTitle(newTitle);
        news.setContent(content);
    }


    private String SiteCheck(String url) {
        List<String> nameList = NewsSources.getNameList();
        //遍历nameList,如果url包含nameList中的某个字符串，则调用getSiteTypeByCode方法，将SiteType的code传入news中
        for (int i = 0; i < nameList.size(); i++) {
            if (url.contains(nameList.get(i))) {
                return NewsSources.getSiteTypeByName(nameList.get(i)).getCode();
            }
        }
        return null;
    }

    private Document getDocument(String url) {
        //正则检查url是否合法，不合法则抛出异常
        if (!url.matches("^(http|https)://.*")) {
            throw new IllegalArgumentException("url不合法");
        }
        //http访问url，获取html
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            throw new IllegalArgumentException("http访问url，获取html失败");
        }
        return doc;
    }

    private String getTitle(Document doc, NewsSources newsSources) {
        String title;
        title = doc.title();
        if (title.length() > 0) {
            int suffixLength = newsSources.getSuffixLength();
            //删除title最后面的suffixLength个字符
            title = title.substring(0, title.length() - suffixLength);
        }
        return title;
    }

    private DateTime getDateTime(NewsSources newsSources, Elements allElements, int i) {
        String date;
        date = allElements.get(i).text();
        //去除date中的所有汉字
        date = date.replaceAll("[\u4e00-\u9fa5]", "");
        //去除第一个字符，直到第一个字符为数字2
        while (!date.substring(0, 1).equals("2")) {
            date = date.substring(1);
        }
        //去除最后一个字符，直到最后一个字符为数字
        while (!date.substring(date.length() - 1).matches("[0-9]")) {
            date = date.substring(0, date.length() - 1);
        }
        DateTime parse = null;
        //使用hutool工具类，将date转换为Date类型
        if (Objects.isNull(newsSources.getDateFormat())) {
            parse = DateUtil.parse(date);
        } else {
            String dateFormat = newsSources.getDateFormat();
            //将dateFormat以逗号分割，得到一个数组
            String[] split = dateFormat.split(",");
            for (String s : split) {
                try {
                    parse = DateUtil.parse(date, s);
                } catch (Exception e) {
                    continue;
                }
                break;
            }
        }
        return parse;
    }

    private String getContent(Elements allElements, int i) {
        String content;
        content = allElements.get(i).toString();
        //取出img标签的src数据
        if (content.contains("<img")) {
            int imgStartIndex = content.indexOf("<img");
            int imgEndIndex = content.indexOf(">", imgStartIndex);
            String imgSrc = content.substring(imgStartIndex, imgEndIndex);
            imgSrc = imgSrc.substring(imgSrc.indexOf("src=") + 5, imgSrc.indexOf("\"", imgSrc.indexOf("src=") + 5));
            content = content.replace(content.substring(imgStartIndex, imgEndIndex), "[img]" + imgSrc + "[/img]");
        }
        // 将]>替换成]
        content = content.replace("]>", "]");
        return content;
    }



}
