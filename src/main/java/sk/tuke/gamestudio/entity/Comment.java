package sk.tuke.gamestudio.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Comment.getComments",
            query = "SELECT c FROM Comment c WHERE c.game = :game")
@NamedQuery( name = "Comment.resetComment",
            query = "DELETE FROM Comment")

public class Comment implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private int ident;

    private String game;
    private String player;
    private String comment;

    @Column(name = "commented_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date commented_on;

    public Comment() {}

    public Comment(String game, String player, String comment, Date commentedOn) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.commented_on = commentedOn;
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

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommented_on() {
        return  this.commented_on;
    }

    public void setCommented_on(Date commentedOn) {
        this.commented_on = commentedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", comment=" + comment +
                ", commentedOn=" + commented_on +
                '}';
    }

}
