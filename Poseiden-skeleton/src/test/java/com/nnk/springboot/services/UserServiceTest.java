package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceTest extends TestCase {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUserList_Should_Get_All_USer(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        userService.getUserList();
        verify(userRepository,times(1)).findAll();
    }

    @Test
    public void getUserById_Should_Get_USer(){
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        userService.getUserById(1);
        verify(userRepository,times(1)).findById(1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getUserById_Should_Throw_Exception(){
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        userService.getUserById(1);
        verify(userRepository,times(1)).findById(1);
    }

    @Test
    public void addUser_Should_Add_USer(){
        User user = new User();
        user.setPassword("testPassword");
        when(userRepository.save(user)).thenReturn(new User());
        userService.addUser(user);
        verify(userRepository,times(1)).save(user);
    }

    @Test
    public void updateUser_Should_Update_User(){
        User userToUpdate = new User();
        userToUpdate.setId(1);

        User user = new User("test01","test02","test03","test04");
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(userToUpdate)).thenReturn(new User());

        userService.updateUserById(1,user);

        verify(userRepository,times(1)).save(userToUpdate);
    }

    @Test
    public void deleteUserById_Should_Delete_USer(){
        User userToDelete = new User();
        userToDelete.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(userToDelete));
        userService.deleteUserById(1);
        verify(userRepository,times(1)).delete(userToDelete);
    }
}