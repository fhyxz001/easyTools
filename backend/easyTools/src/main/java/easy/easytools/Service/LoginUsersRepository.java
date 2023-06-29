package easy.easytools.Service;

import easy.easytools.Entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUsersRepository extends JpaRepository<LoginUser,Integer> {
}
