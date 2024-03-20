package com.github.super_mall.service.authService;

import com.github.super_mall.dto.loginDTO.LoginDTO;
import com.github.super_mall.dto.loginDTO.LoginResponseDto;
import com.github.super_mall.dto.refreshTokenDto.RefreshTokenDto;
import com.github.super_mall.dto.signupDto.SignupDTO;
import com.github.super_mall.dto.userDto.UserDto;
import com.github.super_mall.entity.refreshTokenEntity.RefreshToken;
import com.github.super_mall.entity.roleEntity.Role;
import com.github.super_mall.entity.userEntity.User;
import com.github.super_mall.entity.userRoleEntity.UserRole;
import com.github.super_mall.exceptions.LoginException;
import com.github.super_mall.repository.refreshTokenRepository.RefreshTokenRepository;
import com.github.super_mall.repository.roleRepository.RoleRepository;
import com.github.super_mall.repository.userRepository.UserRepository;
import com.github.super_mall.repository.userRoleRepository.UserRoleRepository;
import com.github.super_mall.util.Constants;
import com.github.super_mall.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public UserDto signup(SignupDTO signupDTO) {
        User newUser = User.builder()
                .email(signupDTO.getEmail())
                .password(passwordEncoder.encode(signupDTO.getPassword()))
                .address("aaaa")
                .build();

        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow(
                () -> new LoginException("Role을 찾을 수 없습니다")
        );

        User saveUser = userRepository.save(newUser);
        UserRole userRole = UserRole.builder()
                .user(saveUser)
                .role(role)
                .build();

        userRoleRepository.save(userRole);

        return new UserDto(saveUser);
    }

    public LoginResponseDto login(LoginDTO loginDTO, HttpServletResponse response) {

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User findUser = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new LoginException("회원을 찾을 수 없습니다")
        );

        if (findUser.getDeletedAt() != null) {
            throw new LoginException("회원을 찾을 수 없습니다");
        }

        if(passwordEncoder.matches(findUser.getPassword(), loginDTO.getPassword())) {
            throw new LoginException("비밀번호가 맞지 않슴니다.");
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(findUser);

        String accessToken = jwtTokenUtil.createAccessToken(findUser.getEmail());
        response.addHeader(Constants.HEADER_ACCESSTOKEN_KEY, accessToken);
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserUserId(findUser.getUserId());

        loginResponseDto.setAccessToken(accessToken);


        if(refreshToken.isEmpty()) {
            String newRefreshToken = jwtTokenUtil.createRefreshToken(findUser.getEmail());
            RefreshToken newRefreshTokenEntity = RefreshToken.builder().user(findUser).refreshToken(newRefreshToken).build();
            refreshTokenRepository.save(newRefreshTokenEntity);
            response.addHeader(Constants.HEADER_REFRESHTOKEN_KEY, newRefreshToken);
            loginResponseDto.setRefreshToken(newRefreshToken);
        } else {
            response.addHeader(Constants.HEADER_REFRESHTOKEN_KEY, refreshToken.get().getRefreshToken());
            loginResponseDto.setRefreshToken(refreshToken.get().getRefreshToken());
        }

        return loginResponseDto;
    }


    public String refresh(RefreshTokenDto refreshTokenDto, HttpServletResponse response) {
        if( refreshTokenDto.getRefreshToken() == null) {
            throw new LoginException("refreshToken이 없습니다.");
        }


        User user = userRepository.findByEmail(refreshTokenDto.getEmail()).orElseThrow(
                () -> new LoginException("회원을 찾을 수 없습니다")
           );

        RefreshToken refreshToken = refreshTokenRepository.findByUserUserId(user.getUserId()).orElseThrow(
                () -> new LoginException("refresh Token을 찾을 수 없습니다")
        );

        if (jwtTokenUtil.isExpired(refreshToken.getRefreshToken())) {
            refreshTokenRepository.delete(refreshToken);
            throw new LoginException("refreshToken의 기간이 만료 되었습니다. 다시 로그인 해주세요");
        }

        if (!refreshToken.getRefreshToken().equals(refreshTokenDto.getRefreshToken())) {
            throw new LoginException("accessToken을 발급 할 수 없습니다.");
        }

        response.addHeader(Constants.HEADER_ACCESSTOKEN_KEY, jwtTokenUtil.createAccessToken(refreshTokenDto.getEmail()));
        return "accessToken이 발급 되었습니다.";

    }

    public UserDto secession(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new LoginException("유저가 없습니다")
        );

        Date now = new Date();
        LocalDateTime localDateTime = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        user.setDeletedAt(localDateTime);

        User updateUser = userRepository.save(user);
        return new UserDto(updateUser);
    }
}
