package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;

/**
 * Interface for user service operations.
 */
public interface UserService {

    /**
     * Register a new user.
     * @param user The user to register
     */
    void register(User user);

    /**
     * Authenticate a user.
     * @param username The username to authenticate
     * @param password The password to authenticate
     * @return The authenticated user or null if authentication fails
     */
    User login(String username, String password);

    /**
     * Find a user by username.
     * @param username The username to search for
     * @return The user with the given username or null if not found
     */
    User findByUsername(String username);

    /**
     * Update a user's profile.
     * @param user The user to update
     */
    void updateUser(User user);

    /**
     * Update a user's level progression.
     * @param username The username of the user
     * @param level The level that the user has just completed
     * @return true if the user's progression was updated, false otherwise
     */

}