package com.oliver.utils;

import com.oliver.common.Const;
import com.oliver.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {//JWT:JSON WEB TOKEN
  private static final String CLAIM_KEY_USERNAME = "sub";
  private static final String CLAIM_KEY_CREATED = "created";
  //从token中获得用户名
  public String getUsernameFromToken(String token) {
    String username;
    try {
      final Claims claims = getClaimsFromToken(token);
      username = claims.getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }
  //获得token生成时间
  public Date getCreatedDateFromToken(String token) {
    Date created;
    try {
      final Claims claims = getClaimsFromToken(token);
      created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
    } catch (Exception e) {
      created = null;
    }
    return created;
  }
  //获得token中的过期日期
  public Date getExpirationDateFromToken(String token) {
    Date expiration;
    try {
      final Claims claims = getClaimsFromToken(token);
      expiration = claims.getExpiration();
    } catch (Exception e) {
      expiration = null;
    }
    return expiration;
  }
  //从token中获得声明
  private Claims getClaimsFromToken(String token) {
    Claims claims;
    try {
      claims = Jwts.parser()
          .setSigningKey(Const.SECRET)
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      claims = null;
    }
    return claims;
  }
  //生成过期日期
  private Date generateExpirationDate() {
    return new Date(System.currentTimeMillis() + Const.EXPIRATION_TIME * 1000);
  }
  // 判断token是否过期
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }
  //判断是否是更新密码前的token
  private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
    return lastPasswordReset != null && created.before(lastPasswordReset);
  }
  //生成token
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<String, Object>();
    claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }
  //生成token
  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(generateExpirationDate())
        .signWith(SignatureAlgorithm.HS512, Const.SECRET)
        .compact();
  }
  //判断是否需要刷新
  public Boolean canTokenBeRefreshed(String token){
    return !isTokenExpired(token);
  }
  //刷新token
  public String refreshToken(String token){
    String refreshedToken;
    try{
      final Claims claims = getClaimsFromToken(token);
      claims.put(CLAIM_KEY_CREATED,new Date());
      refreshedToken = generateToken(claims);
    }catch (Exception e){
      refreshedToken = null;
    }
    return refreshedToken;
  }
  //验证token
  public Boolean validateToken(String token, UserDetails userDetails){
    User user = (User)userDetails;
    final String username = getUsernameFromToken(token);
    return username.equals(user.getUsername())&&!isTokenExpired(token);
  }

  //生成过期token
  public String generateExpiredToken(String token){
    Claims claims = getClaimsFromToken(token);
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(new Date())
        .signWith(SignatureAlgorithm.HS512, Const.SECRET)
        .compact();
  }
}
