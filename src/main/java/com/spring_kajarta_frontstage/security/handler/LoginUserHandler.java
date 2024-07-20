package com.spring_kajarta_frontstage.security.handler;

import com.kajarta.demo.utils.RedisUtil;
import com.spring_kajarta_frontstage.security.handler.vo.LoginUserSession;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@EnableScheduling
public class LoginUserHandler {

    // 24小時
    private final static long SESSION_TIME_OUT = 86400_000L;

    @Autowired
    private RedisUtil redisUtil;

    private static Map<Long, List<LoginUserSession>> loginUserSessionMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        // 重新啟動後清除所有session
        redisUtil.deletePattern("spring:session:*");
    }


    /**
     * 踢出用戶
     */
    public void kickLoginUser(Long adminId) {
        if (loginUserSessionMap.containsKey(adminId)) {
            List<LoginUserSession> userSession = loginUserSessionMap.get(adminId);
            if (userSession != null) {
                userSession.forEach(v -> {
                    redisUtil.deletePattern("spring:session:*" + v.getSessionId());
                    log.info("管理員[{}]下線 token: {}", adminId, v.getSessionId());
                });
                loginUserSessionMap.remove(adminId);
            }
        }
    }


    /**
     * 快取登入用戶sessionId
     */
    public void addLoginUserSessionId(Long adminId, String sessionId) {
        List<LoginUserSession> userSession = Optional.ofNullable(loginUserSessionMap.get(adminId))
                                                     .orElse(new ArrayList<>());
        userSession.add(new LoginUserSession(adminId, sessionId, System.currentTimeMillis()));
        loginUserSessionMap.put(adminId, userSession);
    }

    /**
     * 每隔1分鐘偵測一次session創建時間, 剔除24小時之前的session
     * */
    @Scheduled(cron = "0 */1 * * * ?")
    public void checkService() {
        Date now = new Date();
        Set<Long> adminIds = loginUserSessionMap.keySet();
//        log.info("session check: {}", loginUserSessionMap.size());
        for (Long adminId : adminIds) {
            List<LoginUserSession> userSession = loginUserSessionMap.get(adminId);
            if (userSession != null) {
                boolean expired = false;
                for (int i = 0; i < userSession.size(); i++) {
                    LoginUserSession v = userSession.get(i);
                    if (now.getTime() - v.getLoginTime() > SESSION_TIME_OUT) {
                        expired = true;
                        redisUtil.deletePattern("spring:session:*" + v.getSessionId());
                        log.info("管理員[{}]自動下線 token: {}", adminId, v.getSessionId());
                    }
                }
                if (expired) {
                    loginUserSessionMap.remove(adminId);
                }
            }
        }
    }

}
