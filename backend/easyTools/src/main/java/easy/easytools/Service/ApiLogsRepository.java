package easy.easytools.Service;

import easy.easytools.Entity.ApiLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiLogsRepository extends JpaRepository<ApiLogs,Integer> {
}
