package sk.tuke.gamestudio.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery( name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game")
@NamedQuery( name = "Rating.resetRating",
        query = "DELETE FROM Rating ")
@NamedQuery(name = "Rating.getRatingForPlayer",
        query = "SELECT r FROM Rating r WHERE r.player = :player AND r.game = :game")

public class Rating implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "rating_id")
    private int ident; //identifikator

    private String game;
    private String player;
    private int rating;

    @Column(name = "rated_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date rated_on;

    public Rating() {}

    public Rating(String game, String player, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.rated_on = ratedOn;
    }

    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRated_on() {
        return rated_on;
    }

    public void setRated_on(Date ratedOn) {
        this.rated_on = ratedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + rated_on +
                '}';
    }

}
