package ru.shapovalov;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ru.shapovalov.security.UserRole.ADMIN;
import static ru.shapovalov.security.UserRole.USER;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login-form", "/register", "/api/*").permitAll()
                .antMatchers("/personal-area", "/add-user").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/account-create", "/account-delete", "/account-list").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/category-create", "/category-delete", "/category-edit", "/category-list").hasAnyRole(USER.name(), ADMIN.name())
                .antMatchers("/transaction-list", "/transaction-expense", "/transaction-income", "/transaction-list", "/transaction-receipt", "transaction-transfer").hasAnyRole(USER.name(), ADMIN.name())
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/personal-area")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout-success")
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}