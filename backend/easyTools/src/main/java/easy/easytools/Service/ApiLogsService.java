package easy.easytools.Service;

import easy.easytools.Entity.ApiLogs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiLogsService {
    private final ApiLogsRepository apiLogsRepository;

    public ApiLogsService(ApiLogsRepository apiLogsRepository) {
        this.apiLogsRepository = apiLogsRepository;
    }

    //新增日志的方法
    public ApiLogs saveApiLogs(ApiLogs apiLogs){
        return apiLogsRepository.save(apiLogs);
    }

    //获取所有日志的方法
    public List<ApiLogs> getAllApiLogs(){
        return apiLogsRepository.findAll();
    }
}
