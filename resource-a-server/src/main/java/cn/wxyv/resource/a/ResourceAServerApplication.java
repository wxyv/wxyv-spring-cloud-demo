package cn.wxyv.resource.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableFeignClients
@EnableOAuth2Client
@SpringBootApplication
public class ResourceAServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceAServerApplication.class, args);
    }

}
