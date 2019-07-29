package cn.wxyv.oauthserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author wangxiaoyu
 * @Description: 返回用户信息
 */
@RestController
public class UserController {

    @Autowired
    private InMemoryTokenStore inMemoryTokenStore;

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/oauth/remove_token")
    public String removeToken(@RequestParam("token") String token) {

        if (token != null) {
            OAuth2AccessToken accessToken = inMemoryTokenStore.readAccessToken(token);
            inMemoryTokenStore.removeAccessToken(accessToken);
        } else {
            return "token is empty";
        }

        return "ok";
    }

}
