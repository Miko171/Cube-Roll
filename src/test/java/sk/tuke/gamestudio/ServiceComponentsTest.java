package sk.tuke.gamestudio;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;


import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ServiceComponentsTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;


    @Test
    @Transactional
    void testAddAndGetComments() {
        Comment comment1 = new Comment("TestComment", "Alice", "Super hra!", new Date());
        Comment comment2 = new Comment("TestComment", "Peto", "Dosť tažká hra", new Date());

        commentService.addComment(comment1);
        commentService.addComment(comment2);

        List<Comment> comments = commentService.getComments("TestComment");
        assertEquals("Super hra!", comments.get(0).getComment());
        assertEquals("Dosť tažká hra", comments.get(1).getComment());
    }

    @Test
    @Transactional
    void testAddAndGetScores() {
        Score score1 = new Score("TestScore", "Alica", 500, new Date());
        Score score2 = new Score("TestScore", "Peto", 800, new Date());

        scoreService.addScore(score1);
        scoreService.addScore(score2);

        List<Score> scores = scoreService.getTopScores("TestScore");

        assertEquals(800, scores.get(0).getPoints());
    }

    @Test
    @Transactional
    void testSetAndGetRating() {
        ratingService.setRating(new Rating("TestRating", "Alica", 4, new Date()));
        ratingService.setRating(new Rating("TestRating", "Peto", 2, new Date()));

        assertEquals(4, ratingService.getRating("TestRating", "Alica"));
        assertEquals(2, ratingService.getRating("TestRating", "Peto"));

        assertEquals(3, ratingService.getAverageRating("TestRating")); // Priemer (4+2)/2
    }

    @Test
    @Transactional
    void testUpdateRating() {
        ratingService.setRating(new Rating("TestRating", "Alica", 2, new Date()));
        ratingService.setRating(new Rating("TestRating", "Alica", 4, new Date())); // Update rating

        assertEquals(4, ratingService.getRating("TestRating", "Alica"));
    }

   /* @Test
    @Transactional
    void testResetServices() {
        commentService.addComment(new Comment("TestComment", "Peto", "Nice!", new Date()));
        scoreService.addScore(new Score("TestScore", "Peto", 100, new Date()));
        ratingService.setRating(new Rating("TestRating", "Peto", 5, new Date()));

        commentService.reset();
        scoreService.reset();
        ratingService.reset();

        assertTrue(commentService.getComments("TestComment").isEmpty());
        assertTrue(scoreService.getTopScores("TestScore").isEmpty());
        assertEquals(0, ratingService.getAverageRating("TestRating"));
    }*/
}