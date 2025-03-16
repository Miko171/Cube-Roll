package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "Miko171";
    //public static final String SELECT = "SELECT game, player, rating, ratedOn FROM rating WHERE game = ? ORDER BY ratedOn DESC";
    public static final String SELECT = "SELECT rating FROM rating WHERE game = ? AND player = ?";
    public static final String SELECT_AVG = "SELECT AVG(rating) FROM rating WHERE game = ?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE rating SET rating = ?, ratedOn = ? WHERE game = ? AND player = ?";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (getRating(rating.getGame(), rating.getPlayer()) == -1) {
                try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
                    statement.setString(1, rating.getGame());
                    statement.setString(2, rating.getPlayer());
                    statement.setInt(3, rating.getRating());
                    statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    statement.executeUpdate();
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
                    statement.setInt(1, rating.getRating());
                    statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    statement.setString(3, rating.getGame());
                    statement.setString(4, rating.getPlayer());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem setting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_AVG)) {

            statement.setString(1, game);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    double avgRating = rs.getDouble(1);
                    return (int) Math.round(avgRating);
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem retrieving average rating", e);
        }
        return 0; // Ak nie sú žiadne hodnotenia
    }

     @Override
     public int getRating(String game, String player) throws RatingException {
         try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
              PreparedStatement statement = connection.prepareStatement(SELECT)) {
             statement.setString(1, game);
             statement.setString(2, player);

             try (ResultSet rs = statement.executeQuery()) {
                 if (rs.next()) {
                     return rs.getInt("rating");
                 }
             }
         } catch (SQLException e) {
             throw new RatingException("Problem retrieving rating", e);
         }
         return -1;
     }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new ScoreException("Problem deleting score", e);
        }
    }
}
