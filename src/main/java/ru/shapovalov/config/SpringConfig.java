

package ru.shapovalov.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("ru.shapovalov")
@EntityScan("ru.shapovalov.entity")
@EnableJpaRepositories("ru.shapovalov.repository")
public class SpringConfig {
}