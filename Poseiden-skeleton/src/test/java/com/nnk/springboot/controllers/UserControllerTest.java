package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void home_Should_Return_Ok() throws Exception{
        when(userService.getUserList()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @Test
    public void addUserForm_Should_Return_Ok() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void validate_Should_Return_Ok() throws Exception {
        when(userService.addUser(any())).thenReturn(new User());
        when(userService.getUserList()).thenReturn(new ArrayList<>());
        mockMvc.perform(post("/user/validate")
                        .param("username","test01")
                        .param("password","test02")
                        .param("fullname","test03")
                        .param("role","test04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @Test
    public void validate_Without_Username_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("password","test02")
                        .param("fullname","test03")
                        .param("role","test04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void validate_Without_Password_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username","test01")
                        .param("fullname","test03")
                        .param("role","test04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void validate_Without_Fullname_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username","test01")
                        .param("password","test02")
                        .param("role","test04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void validate_Without_Role_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username","test01")
                        .param("password","test02")
                        .param("fullname","test03")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void showUpdateForm_Should_Return_Ok() throws Exception {
        User user = new User("test01","test02","test03","test04");
        when(userService.getUserById(1)).thenReturn(user);
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
        verify(userService,times(1)).getUserById(1);
    }

    @Test
    public void showUpdateForm_Should_Return_UserList_If_Id_Not_Found() throws Exception {
        when(userService.getUserById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
        verify(userService,times(1)).getUserById(1);
    }

    @Test
    public void updateUser_Should_Return_Ok() throws Exception {
        User user = new User("test01","test02","test03","test04");
        user.setId(1);
        when(userService.updateUserById(1,user)).thenReturn(new User());
        mockMvc.perform(post("/user/update/1")
                        .param("username","test01")
                        .param("password","test02")
                        .param("fullname","test03")
                        .param("role","test04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
        verify(userService,times(1)).updateUserById(1,user);
    }

    @Test
    public void updateUser_Without_Username_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/user/update/1")
                        .param("password","test02")
                        .param("fullname","test03")
                        .param("role","test04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    public void updateUser_Without_Password_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/user/update/1")
                        .param("username","test01")
                        .param("fullname","test03")
                        .param("role","test04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    public void updateUser_Without_Fullname_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/user/update/1")
                        .param("username","test01")
                        .param("password","test02")
                        .param("role","test04")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    public void updateUser_Without_Role_Should_Return_Form() throws Exception {
        mockMvc.perform(post("/user/update/1")
                        .param("username","test01")
                        .param("password","test02")
                        .param("fullname","test03")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    public void deleteUser_Should_Return_Ok() throws Exception {
        when(userService.deleteUserById(1)).thenReturn(new User());
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
        verify(userService,times(1)).deleteUserById(1);
    }

    @Test
    public void deleteUser_Should_Return_UserList_If_Id_Not_Found() throws Exception {
        when(userService.deleteUserById(1)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
        verify(userService,times(1)).deleteUserById(1);
    }
}