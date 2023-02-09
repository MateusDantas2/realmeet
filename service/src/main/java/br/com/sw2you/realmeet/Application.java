package br.com.sw2you.realmeet;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EmailConfigProperties.class)
//TODO lembrar de tirar comment
//@ConfigurationPropertiesScan("br.com.sw2you.realmeet.config.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
