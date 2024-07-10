package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("app")
@SpringBootApplication
public class StartRestServices {
    public static void main(String[] args) {

        SpringApplication.run(StartRestServices.class, args);
    }
}