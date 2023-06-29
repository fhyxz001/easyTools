package easy.easytools.Entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "api_logs")
public class ApiLogs {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    //主键
    private Integer id;
    //接口名称
    private String interfaceName;
    //调用时间
    private String callTime;
    //调用者IP
    private String callerIp;
    //请求参数
    private String requestParams;
    //响应数据
    private String responseData;
    //执行时间
    private Integer executionTime;
    //异常信息
    private String exceptionMessage;
    //创建时间
    private String createdAt;
}
