package com.javatechie.config;

import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

//3.4 classe qui va permettre la recuperation de l user
@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Faudra créer la méthode finByName dans le repo.
        Optional<UserInfo> userInfo = repository.findByName(username);
        //Il faudra convertir toutes les infos de l'userInfo (l'entité) vers l'objet UserDetails (qui vient de security).
        //Pr cette conversion je passse par une nouvelle classe. UserInfoUserDetails.
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}
