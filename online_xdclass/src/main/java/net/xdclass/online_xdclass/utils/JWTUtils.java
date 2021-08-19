package net.xdclass.online_xdclass.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.xdclass.online_xdclass.model.entity.User;

import java.util.Date;

public class JWTUtils {

    private static final long EXPIRE = 60*1000*60*24*7;

    private static final String SECRET = "xdclass.net";

    private static final String TOKEN_PREFIX = "xdclass";

    private static final String SUBJECT = "xdclass";






    public static String geneJsonWebToken(User user){

        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("name", user.getName())
                .claim("id", user.getId())
                .claim("phone", user.getPhone())
                .claim("head_img", user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;

        return token;
    }

    public static Claims checkJWT(String token){

        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

            return claims;
        } catch (Exception e) {
            return null;
        }


    }
}
