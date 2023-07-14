package easy.easytools.Controller;

import easy.easytools.Entity.ApiLogs;
import easy.easytools.Service.ApiLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apiLogs")
public class ApiLogsController {
    @Autowired
    private ApiLogsService apiLogsService;

    //查询所有接口调用日志
    @RequestMapping("/getAllApiLogs")
    public ResponseEntity<List<ApiLogs>> getAllApiLogs(){
        return ResponseEntity.ok(apiLogsService.getAllApiLogs());
    }
}
