//package com.spring_kajarta_frontstage.security.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class TokenService {
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    private final String TOKEN_PREFIX = "TOKEN_";
//
//    // 存储Token到Redis
//    public void storeToken(Long customerId, String token) {
//        redisTemplate.opsForValue().set(TOKEN_PREFIX + token, customerId.toString(), 7, TimeUnit.DAYS);
//    }
//
//    // 从Redis获取Token
//    public String getToken(String token) {
//        return redisTemplate.opsForValue().get(TOKEN_PREFIX + token);
//    }
//}
