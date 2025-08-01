package sk.tuke.gamestudio.entity;


import jakarta.persistence.*;


import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery( name = "Score.getTopScores",
        query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points DESC")
@NamedQuery( name = "Score.resetScores",
        query = "DELETE FROM Score")

public class Score implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "score_id")
    private int ident;

    private String game;

    private String player;

    private int points;

    @Column(name = "played_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date played_on;

    public Score() {}

    public Score(String game, String player, int points, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.played_on = playedOn;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayed_on() {
        return played_on;
    }

    public void setPlayed_on(Date playedOn) {
        this.played_on = playedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + played_on +
                '}';
    }

}
