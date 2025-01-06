package fr.eni.encheres.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails  implements UserDetails {

    private int noUtilisateur;
    private String username;
    private String password;
    private int credit;
    private Collection<? extends GrantedAuthority> authorities;
    
    public CustomUserDetails(int noUtilisateur, String username, String password, int credit, Collection<? extends GrantedAuthority> authorities) {
        this.noUtilisateur = noUtilisateur;
        this.username = username;
        this.password = password;
        this.credit = credit;
        this.authorities = authorities;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "noUtilisateur=" + noUtilisateur +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public int getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(int noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
