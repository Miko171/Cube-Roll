package sk.tuke.gamestudio.service;



import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Rating;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Service
@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void setRating(Rating rating) throws RatingException {
        try {
            // Zistíme, či rating pre daného hráča a hru už existuje
            List<Rating> existingRatings = entityManager
                    .createNamedQuery("Rating.getRatingForPlayer", Rating.class)
                    .setParameter("player", rating.getPlayer())
                    .setParameter("game", rating.getGame())
                    .getResultList();

            if (existingRatings.isEmpty()) {
                // Ak neexistuje, pridáme nový
                entityManager.persist(rating);
            } else {
                // Ak existuje, aktualizujeme existujúci záznam
                Rating existing = existingRatings.get(0);
                existing.setRating(rating.getRating());
                existing.setRated_on(rating.getRated_on());
                entityManager.merge(existing);
            }
        } catch (Exception e) {
            throw new RatingException("Error while setting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try {
            Double avg = (Double) entityManager
                    .createNamedQuery("Rating.getAverageRating")
                    .setParameter("game", game)
                    .getSingleResult();
            if(avg == null){
                return 0; // Ak niesu hodnotenia tak vrat 0
            }
            else {
                return avg.intValue();
            }
        } catch (Exception e){
            throw new RatingException("Failed to get average rating for game: " + game, e);
        }

    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            List<Rating> ratings = entityManager
                    .createNamedQuery("Rating.getRatingForPlayer", Rating.class)
                    .setParameter("player", player)
                    .setParameter("game", game)
                    .getResultList();

            if(!ratings.isEmpty()){
                return ratings.get(0).getRating();
            }
        } catch (Exception e){
            throw new RatingException("Problem retrieving rating", e);
        }
        return 0;
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Rating.resetRating").executeUpdate();
        // alebo:
        // entityManager.createNativeQuery("delete from score").executeUpdate();
    }
}
