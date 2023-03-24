package com.javatechie.config;

import com.javatechie.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//3.5 JE créer cette classe qui va permettre la conversion de l'entity en UserDetails.
public class UserInfoUserDetails implements UserDetails {

    //3.6 j'ajoute ces trois variables.
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    //3.7 je dois créer un constructeur qui prend l'entity et converti les roles. .
    public UserInfoUserDetails(UserInfo userInfo) {
        name=userInfo.getName();
        password=userInfo.getPassword();
        //Plus difficile ici car un utilisateur peut avoir plusieurs rôles donc on récupère un String qui sera séparé en liste selon la virgule.
        authorities= Arrays.stream(userInfo.getRoles().split(","))
                //pr chaque role il crée un nouvel objet SimpleGrantedAuthority
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    //3.8 je mets à jour toutes ces méthodes.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
