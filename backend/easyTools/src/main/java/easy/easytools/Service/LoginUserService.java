package easy.easytools.Service;

import org.springframework.stereotype.Service;

@Service
public class LoginUserService{
    private final LoginUsersRepository loginUsersRepository;
    public LoginUserService(LoginUsersRepository loginUsersRepository) {
        this.loginUsersRepository = loginUsersRepository;
    }
}
