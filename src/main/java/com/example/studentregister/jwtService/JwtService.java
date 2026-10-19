package com.example.studentregister.jwtService;

import com.example.studentregister.user.orm.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

public String generateToken(User user){
    Map<String,Object> extractClaim=new HashMap<>();
    extractClaim.put("userId",user.getId());
    extractClaim.put("username",user.getUsername());
    extractClaim.put("role",user.getRole());
    return  generateToken(extractClaim,user);
}

   // ساخت کلید رمزنگاری برای امضا و اعتبارسنجی JWT
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //خواندن تمام اطلاعات داخل JWT و بررسی امضای آن
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //استخراج تاریخ و زمان انقضای توکن
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //بررسی اعتبار توکن نسبت به یک کاربر مشخص
    public boolean isTokenValid(String token, User user) {
        try {
            String username = extractUsername(token);

            return username.equals(user.getUsername())
                    && !isTokenExpired(token);

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String generateToken(
            Map<String, Object> extractClaim,
            User user) {

        long currentTime = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(extractClaim)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    public class SecretKeyGenerator {
        public static void main(String[] args) {
            String key = Encoders.BASE64.encode(
                    Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()
            );

            System.out.println(key);
        }
    }
}