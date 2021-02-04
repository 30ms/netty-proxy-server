package com.example.datacollector.tasker;

import com.example.datacollector.config.UserTokenStorage;
import com.example.datacollector.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class LoginTasker {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserTokenStorage userTokenStorage;

    @Scheduled(cron = "0 0 0/3 * * ? ")
    public void scheduledTask() {
        boolean success = false;
        int times = 5;
        userTokenStorage.remove();
        while (!success && times > 0) {
            String userToken = null;
            try {
                userToken = loginService.login();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (StringUtils.hasLength(userToken)) {
                success = true;
                userTokenStorage.set(userToken);
            }
            times--;
        }
    }
}
