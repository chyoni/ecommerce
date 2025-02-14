package cwchoiit.ecommerce.user.repository;

import cwchoiit.ecommerce.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
