package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * User Service
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get a list of all user
     * @return a list of all user
     */
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    /**
     * Get a user by his id
     * @param id is the id of the user
     * @return the user or an error if it is not found
     */
    public User getUserById(int id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User : " +id+ " not found"));
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * Add a new user
     * @param user is the user that need be added
     * @return the added user
     */
    public User addUser(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        return userRepository.save(user);
    }


    /**
     * Update a user
     * @param id is the id of the user that need to be updated
     * @param user is the new data for the update
     * @return the updated user
     */
    public User updateUserById(Integer id, User user) {
        User userToUpdate = getUserById(id);
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(encodePassword(user.getPassword()));
        userToUpdate.setFullname(user.getFullname());
        userToUpdate.setRole(user.getRole());
        return userRepository.save(userToUpdate);
    }

    /**
     * Delete a user
     * @param id is the id of the user that need to be deleted
     * @return the deleted user
     */
    public User deleteUserById(Integer id) {
        User userToDelete = getUserById(id);
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    /**
     * Encode a password
     * @param password is the password that need to be encoded
     * @return the encoded password
     */
    private String encodePassword(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
