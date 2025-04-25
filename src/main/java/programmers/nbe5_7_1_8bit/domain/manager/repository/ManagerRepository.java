package programmers.nbe5_7_1_8bit.domain.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import programmers.nbe5_7_1_8bit.domain.manager.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

}
