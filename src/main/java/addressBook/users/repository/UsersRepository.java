package addressBook.users.repository;

import addressBook.users.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByPhoneAndName(String phone, String name);
    boolean existsByPhoneAndNameAndIdNot(String phone, String name, Long id);
    @Query("SELECT u FROM Users u WHERE u.name = :name OR u.name LIKE %:name%")
    List<Users> findAllByNameExactOrContaining(String name);

}
