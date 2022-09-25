package neonaduri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class NeonaduriApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yaml,"
            + "classpath:aws.yml";
    public static void main(String[] args) {
        new SpringApplicationBuilder(NeonaduriApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
