package com.javatechie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


//1.0 Première classe qu'on créé.
@Configuration
@EnableWebSecurity
//2.2 Doit ajouter cela car j'ai ajouté des preAuthorize
@EnableMethodSecurity
public class SecurityConfig {

    //1.1 On créé ce bean qui remplace la méthode qu'on ovveridait avec l'adaptater.
    @Bean
    //authentication - On peut y définir autant de userDetails que l'on souhaite
    // 1.4 C'est évidemment pas une bonne méthode. Il faut l'encrypter. Je le fais en rajoutant un encoder en paramètre.

    //5.1 Je retire PasswordEncoder encoder du paramètre de la méthode pour faire le 5.0
    public UserDetailsService userDetailsService() {
        //3.2 je mets toute cette partie en commentaire car je vais passer par la bdd.

//        UserDetails admin = User.withUsername("Seba")
//                //on voit ici je j'utilise l'encoder.
//                .password(encoder.encode("1234"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("John")
//                .password(encoder.encode("Pwd2"))
//                .roles("USER","ADMIN","HR")
//                .build();
//        //Là je les mets en mémoire.
//        return new InMemoryUserDetailsManager(admin, user);

        //3.3 il faudra créer cette classe.
        return new UserInfoUserDetailsService();
    }

    //1.3 ICi on définit les accès et restrictions pour chaque endPoint
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                //J'ajoute le product/new pour permettre de créer un nvl utilisateur.
                .requestMatchers("/products/welcome","/products/new").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/products/**")
                .authenticated()
                .and()
                //ici je lui dis comment je veux être authentifié - Avec un formulaire
                .formLogin()
                .and().build();
    }


    //1.2 Je crée un encoder.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //5.0 Je dois ajouter cette méthode pour qu'une authorisation soit fournie.
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
