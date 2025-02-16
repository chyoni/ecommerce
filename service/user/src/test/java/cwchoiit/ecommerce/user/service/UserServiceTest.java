package cwchoiit.ecommerce.user.service;

import cwchoiit.ecommerce.user.entity.Users;
import cwchoiit.ecommerce.user.repository.UsersRepository;
import cwchoiit.ecommerce.user.service.request.CreateUserRequest;
import cwchoiit.ecommerce.user.service.response.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UsersRepository usersRepository;

    @Test
    void createUser() {
        CreateUserResponse createdUser = userService.createUser(
                CreateUserRequest.of(
                        "noreply@noreply.com",
                        "noreply",
                        "noreply"
                )
        );

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo("noreply@noreply.com");
        assertThat(createdUser.getName()).isEqualTo("noreply");

        Users user = usersRepository.findByUserId(createdUser.getUserId()).orElseThrow();

        assertThat(user.getEmail()).isEqualTo(createdUser.getEmail());
        assertThat(user.getName()).isEqualTo(createdUser.getName());
        assertThat(user.getUserId()).isEqualTo(createdUser.getUserId());
    }
}