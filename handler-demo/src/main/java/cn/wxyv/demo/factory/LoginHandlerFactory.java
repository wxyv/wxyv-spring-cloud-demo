package cn.wxyv.demo.factory;

import cn.wxyv.demo.enums.LoginModeEnum;
import cn.wxyv.demo.handler.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginHandlerFactory {

    private static Map<String, LoginHandler> map;

    @Autowired
    public LoginHandlerFactory(Map<String, LoginHandler> map) {
        LoginHandlerFactory.map = map;
    }

    public static LoginHandler getHandler(LoginModeEnum loginModeEnum) {
        return map.get(loginModeEnum.getValue());
    }
}
