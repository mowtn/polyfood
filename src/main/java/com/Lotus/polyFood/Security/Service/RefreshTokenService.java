package com.Lotus.polyFood.Security.Service;

import com.Lotus.polyFood.Model.RefreshToken;
import com.Lotus.polyFood.Repository.RefreshTokenRepository;
import com.Lotus.polyFood.Repository.UserRepository;
import com.Lotus.polyFood.Security.Exception.TokenRefreshException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(int userId) {
        Optional<RefreshToken> token = refreshTokenRepository.findByUserId(userId);
        RefreshToken refreshToken = new RefreshToken();
        if (token.isPresent()) {
            if (token.get().getExpiryDate().isBefore(Instant.now())){
                refreshTokenRepository.deleteByUserId(userId);
                refreshToken.setUser(userRepository.findById(userId).get());
                refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
                refreshToken.setToken(UUID.randomUUID().toString());
                refreshToken = refreshTokenRepository.save(refreshToken);
            }else {
                refreshToken = token.get();
            }
        }
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(int userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }
}
