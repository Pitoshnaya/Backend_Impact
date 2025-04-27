package Pitoshnaya.Impact.entity;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User implements UserDetails {
    @Id                                                             ////глянь
    @GeneratedValue(strategy= GenerationType.IDENTITY)              ////глянь
    private Long id;

    @Column(unique=true)                                            ////глянь
    private String username;

    private String password;

    public Long getId(){
        return id ;
    }
    public void setId(Long id){
        this.id=id;
    }
    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
    //реализовать методы userdetails
}
