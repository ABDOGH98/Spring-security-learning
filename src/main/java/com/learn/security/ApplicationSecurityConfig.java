package com.learn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.learn.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // using annotations in controller
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/**","/js/**").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                //.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                //.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                //.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                //.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic();
                .formLogin()
                    //.loginPage("/login").permitAll()
                    .defaultSuccessUrl("/",false)
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)).key("mySecureKey")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))//when enabling CSRF we should delete this ligne. POST as dfault here
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","remember-me")
                    .logoutSuccessUrl("/");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails abdo =  User.builder()
                .username("abdo")
                .password(passwordEncoder.encode("abdo"))
                //.roles(STUDENT.name())//role student
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails ziza =  User.builder()
                .username("ziza")
                .password(passwordEncoder.encode("ziza"))
                //.roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails zozo =  User.builder()
                .username("zozo")
                .password(passwordEncoder.encode("zozo"))
                //.roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(abdo,ziza,zozo);
    }
}
