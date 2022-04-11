package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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

    /**
     * Add a new user
     * @param user is the user that need be added
     * @return the added user
     */
    public User addUser(User user) {
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
        userToUpdate.setPassword(user.getPassword());
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
}
