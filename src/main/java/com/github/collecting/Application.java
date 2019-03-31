package com.github.collecting;


import com.github.collecting.jpa.base.PlatformBaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = PlatformBaseRepository.class)
public class Application {

    @Bean
    public ApplicationPidFileWriter applicationPidFileWriter() {
        return new ApplicationPidFileWriter();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
