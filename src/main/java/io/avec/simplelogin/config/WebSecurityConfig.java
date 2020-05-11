package io.avec.simplelogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll() // Open for all
                    .anyRequest().authenticated() // All others requires authentication
                    .and()
                .formLogin()
//                    .loginPage("/login") // requires /login page to exist
                    .permitAll() // when login ok, permit all
                    .and()
                .logout()
                    .permitAll();
    }


    /*
        Simulate some users InMemory
        For DB users see e.g. https://www.baeldung.com/spring-security-authentication-with-a-database
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails demo =
                User.withDefaultPasswordEncoder()
                        .username("demo")
                        .password("demo")
                        .roles("USER")
                        .build();

        UserDetails test =
                User.withDefaultPasswordEncoder()
                        .username("test")
                        .password("test")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(demo, test);
    }
}
