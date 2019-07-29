package cn.wxyv.resource.b.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ServiceBController {

    private String msg = "hello";

    @GetMapping(value = "/")
    public String printServiceB() {
        return "===>Say " + msg;
    }

    @GetMapping(path = "/current")
    public String getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}