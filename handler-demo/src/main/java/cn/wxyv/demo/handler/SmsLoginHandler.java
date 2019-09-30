package cn.wxyv.demo.handler;

import org.springframework.stereotype.Component;

@Component(SmsLoginHandler.HANDLER_NAME)
public class SmsLoginHandler implements LoginHandler {
    public static final String HANDLER_NAME = "smsLoginHandler";

    @Override
    public boolean handler() {
        System.out.println("短信登录处理");
        return true;
    }
}
