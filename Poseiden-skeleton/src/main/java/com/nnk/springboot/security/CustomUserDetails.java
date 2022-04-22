package com.nnk.springboot.security;

import com.nnk.springboot.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Custom UserDetails
 */
public class CustomUserDetails implements UserDetails {

    private User user;

    /**
     *Construct the class with a User
     * @param user who will be used to construct CustomUserDetails
     */
    public CustomUserDetails(User user){
        this.user = user;
    }

    /**
     * Get the authorities
     * @return a list of GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
        grantedAuthorityList.add(simpleGrantedAuthority);
        return grantedAuthorityList;
    }

    /**
     * Get the password
     * @return the password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Get the username
     * @return the username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Check if the account is non expired
     * @return always true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the account is non locked
     * @return always true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the credential is non expired
     * @return always true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check the status of enabled
     * @return always true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
