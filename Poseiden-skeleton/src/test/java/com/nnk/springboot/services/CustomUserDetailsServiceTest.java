package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest extends TestCase {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void loadUserByUsernameShouldReturnUserDetails() {
        String username = "TestUsername";
        when(userService.getUserByUsername(username)).thenReturn(Optional.of(new User()));

        UserDetails result = customUserDetailsService.loadUserByUsername(username);

        assertThat(result).isNotNull();
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameShouldThrowException() {
        String username = "TestUsername";
        when(userService.getUserByUsername(username)).thenReturn(Optional.empty());

        customUserDetailsService.loadUserByUsername(username);
    }
}