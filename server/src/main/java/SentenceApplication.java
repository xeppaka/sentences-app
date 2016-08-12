import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "com.xeppaka.sentence")
public class SentenceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentenceApplication.class, args);
    }
}
