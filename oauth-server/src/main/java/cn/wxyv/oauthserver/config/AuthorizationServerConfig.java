package cn.wxyv.oauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author wangxiaoyu
 * @Description: 认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public InMemoryTokenStore tokenStore() {
        return new InMemoryTokenStore();
    }


    /**
     * 配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .passwordEncoder(passwordEncoder);
    }

    /**
     * 配置客户端详情信息(Client Details)
     * clientId：（必须的）用来标识客户的Id。
     * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
     * authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client1")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("authorization_code")
                // 允许的授权范围
                .scopes("app")

                .and()
                .withClient("client")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("password", "refresh_token")
                .resourceIds("oauth-resource","a-resource")
                .scopes("read", "write")
                // token有效期：单位秒   1 hour
                .accessTokenValiditySeconds(3600)
                // 刷新token有效期：单位秒 30 days
                .refreshTokenValiditySeconds(2592000)
                // "authorization_code" 授权码方式登录后 绕过批准询问(/oauth/confirm_access)
                .autoApprove(true)

                .and()
                .withClient("resource-a-service")
                .secret(passwordEncoder.encode("password"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .resourceIds("b-resource")
                .scopes("server")

                .and()
                .withClient("resource-b-service")
                .secret(passwordEncoder.encode("password"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .redirectUris("user-resource")
                .scopes("server");

    }

    /**
     * 配置AuthorizationServerEndpointsConfigurer众多相关类，包括配置身份认证器，
     * 配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     *
     * 配置授权、令牌的访问端点和令牌服务
     * tokenStore：采用redis储存
     * authenticationManager:身份认证管理器, 用于"password"授权模式
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                //要使用refresh_token的话，需要额外配置userDetailsService
                //.userDetailsService(userDetailsService)
                .reuseRefreshTokens(false);
    }
}
