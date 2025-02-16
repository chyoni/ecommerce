package cwchoiit.ecommerce.user.service;

import cwchoiit.ecommerce.common.snowflake.Snowflake;
import cwchoiit.ecommerce.user.entity.Users;
import cwchoiit.ecommerce.user.repository.UsersRepository;
import cwchoiit.ecommerce.user.service.request.CreateUserRequest;
import cwchoiit.ecommerce.user.service.response.CreateUserResponse;
import cwchoiit.ecommerce.user.service.response.UserPageResponse;
import cwchoiit.ecommerce.user.service.response.UserResponse;
import cwchoiit.ecommerce.user.utils.PageLimitCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final Snowflake snowflake = new Snowflake();
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        Users newUser = usersRepository.save(
                Users.of(
                        snowflake.nextId(),
                        request.getEmail(),
                        request.getName(),
                        bCryptPasswordEncoder.encode(request.getPassword())
                )
        );

        return CreateUserResponse.from(newUser);
    }

    public UserResponse getUserByUserId(Long userId) {
        Users findUser = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserResponse.from(
                findUser.getEmail(),
                findUser.getName(),
                findUser.getUserId(),
                findUser.getCreatedAt(),
                findUser.getModifiedAt());
    }

    public UserPageResponse getUsers(Long page, Long pageSize) {
        List<UserResponse> users = usersRepository.getUsers((page - 1) * pageSize, pageSize).stream()
                .map(user ->
                        UserResponse.from(
                                user.getEmail(),
                                user.getName(),
                                user.getUserId(),
                                user.getCreatedAt(),
                                user.getModifiedAt()
                        )
                )
                .toList();

        return UserPageResponse.from(
                users,
                usersRepository.count(PageLimitCalculator.calculatePageLimit(page, pageSize, 10L))
        );
    }
}
