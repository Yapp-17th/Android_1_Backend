package org.picon.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.picon.auth.entity.Member;
import org.picon.global.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final UserDetailServiceImpl userDetailService;

    @Value("${JWT.SECRET.KEY}")
    private String SECRET_KEY;

    @Value("${JWT.TOKEN.VALIDITY.ACCESS}")
    private Long JWT_ACCESS_TOKEN_VALIDITY;

    @Value("${JWT.TOKEN.VALIDITY.REFRESH}")
    private Long JWT_REFRESH_TOKEN_VALIDITY;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String generateAccessToken(Map<String, Object> claimMap) {
        Date now = new Date();
        Date expireTime = new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY * 1000);
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject("accessToken")
                .setClaims(claimMap)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateAccessTokenBy(Member member) {
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("IDENTITY", member.getIdentity());
        return generateAccessToken(claimMap);
    }

    public String generateAccessTokenBy(String refreshToken) {
        String identity = findIdentityByToken(refreshToken);
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("IDENTITY", identity);
        return generateAccessToken(claimMap);
    }

    public String generateRefreshToken(Member member) {
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("IDENTITY", member.getIdentity());
        Date now = new Date();
        Date expireTime = new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000);
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject("refreshToken")
                .setClaims(claimMap)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("AccessToken");
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String findIdentityByToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("IDENTITY");
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(findIdentityByToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
