package cn.wxyv.resource.a.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

/**
 *
 * 用于服务调用鉴权，配合client的resouceId 使用，feign 调用使用 ClientCredentials 模式获取token 访问
 * @author xywang
 */
@Configuration
public class FeignConfig {

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;

    @Bean
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
        clientCredentialsResourceDetails.setClientId(oAuth2ClientProperties.getClientId());
        clientCredentialsResourceDetails.setClientSecret(oAuth2ClientProperties.getClientSecret());
        clientCredentialsResourceDetails.setAccessTokenUri(accessTokenUri);
        return clientCredentialsResourceDetails;
    }

    /**
     * // @FeignClient(configuration=FeignConfig.class 这样会导致 Interceptor 这个bean会执行两次
     * @return
     */
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }

}
