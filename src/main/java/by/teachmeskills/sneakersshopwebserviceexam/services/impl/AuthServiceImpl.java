package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.config.JwtProvider;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Token;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.JwtRequestDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.JwtResponseDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.UserConverter;
import by.teachmeskills.sneakersshopwebserviceexam.exception.AuthorizationException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.TokenRepository;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.UserRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.AuthService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserConverter userConverter;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public JwtResponseDto login(@NonNull JwtRequestDto authRequest) throws AuthorizationException {
        UserDto user = userRepository.findUserByMail(authRequest.getMail()).map(userConverter::toDto)
                .orElseThrow(() -> new AuthorizationException("User is not found"));
        if (encoder.matches(authRequest.getPassword(), user.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);
            Token tokenEntity = tokenRepository.findByUsername(user.getMail()).orElse(null);
            if (tokenEntity != null) {
                tokenEntity.setToken(refreshToken);
                tokenRepository.save(tokenEntity);
            } else {
                tokenRepository.save(Token.builder().
                        username(user.getMail()).
                        token(refreshToken).
                        build());
            }
            return new JwtResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthorizationException("Wrong password");
        }
    }

    @Override
    public JwtResponseDto getAccessToken(@NonNull String refreshToken) throws AuthorizationException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String saveRefreshToken = tokenRepository.findByUsername(login).map(Token::getToken).orElse(null);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                UserDto user = userRepository.findUserByMail(login).map(userConverter::toDto)
                        .orElseThrow(() -> new AuthorizationException("User is not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponseDto(accessToken, null);
            }
        }
        return new JwtResponseDto(null, null);
    }

    @Override
    @Transactional
    public JwtResponseDto refresh(@NonNull String refreshToken) throws AuthorizationException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            Token tokenEntity = tokenRepository.findByUsername(login).orElse(null);
            if (tokenEntity != null && tokenEntity.getToken().equals(refreshToken)) {
                UserDto user = userRepository.findUserByMail(login).map(userConverter::toDto)
                        .orElseThrow(() -> new AuthorizationException("User is not found"));
                String accessToken = jwtProvider.generateAccessToken(user);
                String newRefreshToken = jwtProvider.generateRefreshToken(user);
                tokenEntity.setToken(newRefreshToken);
                tokenRepository.save(tokenEntity);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        throw new AuthorizationException("Invalid jwt token");
    }

    @Override
    public Optional<UserDto> getPrincipal() {
        return userRepository.findUserByMail(((User) SecurityContextHolder.getContext().getAuthentication().
                getPrincipal()).getUsername()).map(userConverter::toDto);
    }
}
