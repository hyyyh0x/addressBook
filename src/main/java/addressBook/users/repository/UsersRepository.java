package addressBook.users.repository;

import addressBook.users.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByPhoneAndName(String phone, String name);
    boolean existsByPhoneAndNameAndIdNot(String phone, String name, Long id);
    List<Users> findAllByNameContaining(String name);
}
