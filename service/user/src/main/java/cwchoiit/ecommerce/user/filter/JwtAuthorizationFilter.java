package cwchoiit.ecommerce.user.filter;

import cwchoiit.ecommerce.user.entity.Users;
import cwchoiit.ecommerce.user.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UsersRepository usersRepository;

    public JwtAuthorizationFilter(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // API Gateway 에서 토큰을 먼저 인증한 후, 토큰에서 꺼낸 userId 값을 헤더에 포함해서 요청을 전달
        String userId = request.getHeader("X-User-Id");

        if (userId != null) {
            Authentication authentication = getUsernamePasswordAuthenticationToken(userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthenticationToken(String userId) {
        Users findUser = usersRepository.findByUserId(Long.valueOf(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        return new UsernamePasswordAuthenticationToken(findUser, null, findUser.getAuthorities());
    }
}
