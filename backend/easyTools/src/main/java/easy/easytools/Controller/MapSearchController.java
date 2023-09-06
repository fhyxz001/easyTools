package easy.easytools.Controller;

import cn.hutool.json.JSONObject;
import easy.easytools.Entity.Address;
import easy.easytools.Service.MapSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/Map")
@Controller
public class MapSearchController {

    @Autowired
    MapSearchService mapSearchService;
    @PostMapping("/search")
    @ResponseBody
    public JSONObject mapSearch(@RequestBody Map<String,String> param) {
        JSONObject entries = mapSearchService.mapSearch(param);
        return entries;
    }

    @PostMapping("/analyze")
    @ResponseBody
    public JSONObject analyze(@RequestBody List<Address> param){
        JSONObject entries = mapSearchService.analyze(param);
        return entries;
    }
}
