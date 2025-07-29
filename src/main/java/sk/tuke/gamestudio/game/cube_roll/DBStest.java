package sk.tuke.gamestudio.game.cube_roll;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentServiceJDBC;
import sk.tuke.gamestudio.service.RatingServiceJDBC;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;

public class DBStest {
    public static void main(String[] args) {
        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
        RatingServiceJDBC ratingServiceJDBC = new RatingServiceJDBC();

        scoreServiceJDBC.reset();
        commentServiceJDBC.reset();
        ratingServiceJDBC.reset();

        Score score = new Score("Cube Roll","Mato",100, new Date());
        Score score1 = new Score("Cube Roll","Simona",150, new Date());
        Score score2 = new Score("Cube Roll","xP3to",145, new Date());
        Score score3 = new Score("Cube Roll","H4lC3O",140, new Date());
        Score score4 = new Score("Cube Roll","EasyGames",200, new Date());

        Comment comment = new Comment("Cube Roll","Mato","Wow posledný level je fakt tažký",new Date());
        Comment comment1 = new Comment("Cube Roll","Simona","Fuuha medium level mi dal zabrať",new Date());
        Comment comment2 = new Comment("Cube Roll","xP3to", "Dobre spracovaná hra len viac máp treba",new Date());
        Comment comment3 = new Comment("Cube Roll","H4lC3O", " Feest dobra hra.",new Date());
        Comment comment4 = new Comment("Cube Roll","EasyGames", "Neviem aký máte problám ja som prešiel všetky v pohode",new Date());

        Rating rating = new Rating("Cube Roll","Mato",4,new Date());
        Rating rating1 = new Rating("Cube Roll","Simona",3,new Date());
        Rating rating2 = new Rating("Cube Roll","xP3to",5,new Date());
        Rating rating3 = new Rating("Cube Roll","H4lC3O",3,new Date());
        Rating rating4 = new Rating("Cube Roll","EasyGames",5,new Date());

        scoreServiceJDBC.addScore(score);
        scoreServiceJDBC.addScore(score1);
        scoreServiceJDBC.addScore(score2);
        scoreServiceJDBC.addScore(score3);
        scoreServiceJDBC.addScore(score4);

        commentServiceJDBC.addComment(comment);
        commentServiceJDBC.addComment(comment1);
        commentServiceJDBC.addComment(comment2);
        commentServiceJDBC.addComment(comment3);
        commentServiceJDBC.addComment(comment4);

        ratingServiceJDBC.setRating(rating);
        ratingServiceJDBC.setRating(rating1);
        ratingServiceJDBC.setRating(rating2);
        ratingServiceJDBC.setRating(rating3);
        ratingServiceJDBC.setRating(rating4);


        /*System.out.println("Setting rating...");
        ratingServiceJDBC.setRating(rating);
        ratingServiceJDBC.setRating(rating1);
        System.out.println("Rating set successfully!");
        int avrgRating = ratingServiceJDBC.getAverageRating("Testing");
        System.out.println(avrgRating);
        */


    }
}
