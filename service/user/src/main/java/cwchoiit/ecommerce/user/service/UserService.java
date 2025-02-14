package cwchoiit.ecommerce.user.service;

import cwchoiit.ecommerce.user.entity.Users;
import cwchoiit.ecommerce.user.repository.UsersRepository;
import cwchoiit.ecommerce.user.service.request.CreateUserRequest;
import cwchoiit.ecommerce.user.service.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        Users newUser = usersRepository.save(
                Users.of(
                        request.getEmail(),
                        request.getName(),
                        UUID.randomUUID().toString(),
                        request.getPassword()
                )
        );

        return CreateUserResponse.from(newUser);
    }
}
