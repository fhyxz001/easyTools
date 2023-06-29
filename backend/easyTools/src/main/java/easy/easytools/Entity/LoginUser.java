package easy.easytools.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "login_user")
@Data
public class LoginUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //主键
    private Integer id;
    //用户名
    private String username;
    //密码
    private String password;
    //邮箱
    private String email;
    //注册时间
    private String registration_date;
    //用户类型
    private String user_type;
}
