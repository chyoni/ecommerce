package cwchoiit.ecommerce.user.repository;

import cwchoiit.ecommerce.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    /**
     * 페이지 번호 방식의 유저 목록을 조회한다.
     * 참고로, 여기서 SELECT 절에서 가져오는 컬럼들은, 전부 사용하지 않더라도 전부 결과셋에 포함시켜야 한다.
     * JPA 에서는 엔티티 매핑 시, 엔티티 필드에 해당하는 컬럼을 전부 SELECT 해서 결과셋에 포함시켜야 한다.
     * 엔티티를 리턴하는 NativeQuery 를 작성하면, 엔티티의 모든 컬럼에 해당하는 값이 쿼리 결과에 있어야 JPA 가 정상 매핑할 수 있다.
     *
     * @param offset offset
     * @param limit  limit
     * @return {@code List<Users>}
     */
    @Query(
            value = "select id, email, name, user_id, encrypted_password, created_at, modified_at " +
                    "from users " +
                    "order by id desc " +
                    "limit :limit offset :offset ",
            nativeQuery = true
    )
    List<Users> getUsers(@Param("offset") Long offset, @Param("limit") Long limit);

    @Query(
            value = "select count(*) " +
                    "from (" +
                    "   select id " +
                    "   from users " +
                    "   limit :limit " +
                    ") t ",
            nativeQuery = true
    )
    Long count(@Param("limit") Long limit);

    Optional<Users> findByUserId(String userId);
}
