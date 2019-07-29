package cn.wxyv.resource.a.controller;

import cn.wxyv.resource.a.client.ServiceBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RefreshScope
@RestController
public class ServiceAController {

    @Value("${name:unknown}")
    private String name;

    @Autowired
    private ServiceBClient serviceBClient;

    @GetMapping(value = "/")
    public String printServiceA() {
        return "===>name:" + name + "<br/>" + serviceBClient.printServiceB();
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return serviceBClient.getCurrentAccount();
    }

    @GetMapping(path = "/current")
    public Principal getCurrentAccount(Principal principal) {
        return principal;
    }
}