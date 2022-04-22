package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;
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

    /**
     * Get a user by a username
     * @param username is the username to get
     * @return Optional of User
     */
    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * Add a new user
     * @param user is the user that need be added
     * @return the added user
     */
    public User addUser(User user) throws EntityExistsException{
        if (isUserExistByUsername(user.getUsername())){
            throw new EntityExistsException("User déjà existant : "+user.getUsername());
        }
        user.setPassword(encodePassword(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Check if a user exist by a username
     * @param username is the username to check
     * @return true if the username exist
     */
    private boolean isUserExistByUsername(String username) {
        return userRepository.existsByUsername(username);
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

    /**
     * Get the username
     * @return the username
     */
    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            return (String) token.getPrincipal().getAttributes().get("login");
        } else {
            return authentication.getName();
        }
    }

    /**
     * Check if the role is equal to ADMIN
     * @return true if the role is equal to ADMIN
     */
    public boolean isRoleAdmin(){
        return getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    /**
     * Get the authentication
     * @return the authentication
     */
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
