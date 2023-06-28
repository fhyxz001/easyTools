package easy.easytools.Controller;

import easy.easytools.Entity.News;
import easy.easytools.Service.BBSToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bbs")
public class BBStoolController {

    @Autowired
    BBSToolsService BBSToolsService;
            
    @GetMapping("/getNewsByUrl")
    @ResponseBody
    public News getNewsByUrl(@RequestParam("url") String url){
        return BBSToolsService.getNewsByUrl(url);
    }
}
