package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.cube_roll.consoleui.ConsoleUI;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@ComponentScan(
        basePackages = "sk.tuke.gamestudio",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "sk.tuke.gamestudio.server.*"
        )
)
public class SpringClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.play();
    }

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI();
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceRestClient();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}