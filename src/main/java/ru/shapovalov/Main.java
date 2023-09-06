package ru.shapovalov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.shapovalov.security.UserRole;

import java.sql.SQLException;

import static ru.shapovalov.security.UserRole.ADMIN;
import static ru.shapovalov.security.UserRole.USER;

@SpringBootApplication
public class Main extends WebSecurityConfigurerAdapter implements CommandLineRunner {

    @Autowired
    private TerminalView terminalView;

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        terminalView.start();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                 .authorizeRequests()
                        .antMatchers("/login").permitAll()
                        .antMatchers("/login-success").hasAnyRole(USER.name(), ADMIN.name())
                .and()
                    .formLogin()
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/login-success")
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                .and()
                    .httpBasic();



    }
}