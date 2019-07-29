package cn.wxyv.oauthserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wangxiaoyu
 * @Description: sercurity 安全配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 为特定请求配置web的安全性
     * <p>
     * 拦截配置:
     * authorizeRequests()配置路径拦截，表明路径访问所对应的权限，角色，认证信息。
     * formLogin()对应表单认证相关的配置
     * logout()对应了注销相关的配置
     * httpBasic()可以配置basic登录
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/oauth/remove_token").permitAll()
                .antMatchers("/resources/**", "/signup", "/about").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .failureForwardUrl("/login?error")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll()
                .and()
                .httpBasic().disable()
                .csrf().disable();
    }


    /**
     * 用于影响全局安全性(配置资源，设置调试模式，通过实现自定义防火墙定义拒绝请求)的配置设置。
     * 例如，以下方法将导致以/ resources /开头的任何请求被忽略以用于认证目的。
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/favor.ico");
    }

    /**
     * 用于通过允许AuthenticationProvider容易地添加来建立认证机制。
     * 以下定义了内置认证与内置的“用户”和“管理”登录。
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().passwordEncoder(passwordEncoder)
                .withUser("user").password(passwordEncoder.encode("password")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("password")).roles("ADMIN", "USER");

//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//        auth.eraseCredentials(true); //擦除凭证，即密码
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
