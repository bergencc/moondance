package org.bosf.moondance.security;

import org.bosf.moondance.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final User.UserRole role;
    private final Long schoolId;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPasswordHash();
        this.name = user.getName();
        this.role = user.getRole();
        this.schoolId = user.getSchool() != null ? user.getSchool().getId() : null;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        this.enabled = user.getBannedAt() == null && user.getDeletedAt() == null;
    }

    public Long getId() { 
        return id; 
    }

    public String getEmail() { 
        return email; 
    }

    @Override
    public String getPassword() { 
        return password; 
    }

    public String getName() { 
        return name; 
    }

    public User.UserRole getRole() { 
        return role; 
    }

    public Long getSchoolId() { 
        return schoolId; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { 
        return authorities; 
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
