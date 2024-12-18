package fr.eni.encheres.security;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.UtilisateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;
    public UserDetailsServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Utilisateur> userOptional = utilisateurRepository.getByLogin(login);
        if(userOptional.isPresent()) {
            Utilisateur user = userOptional.get();
            SimpleGrantedAuthority role = null;
            if(user.isAdministrateur()) {
                role = new SimpleGrantedAuthority("ROLE_ADMIN");
            } else {
                role = new SimpleGrantedAuthority("ROLE_USER");
            }
            List<GrantedAuthority> roles = List.of(role);

            return User.builder().username(user.getEmail()).password(user.getMotDePasse()).authorities(roles).build();
        }

        throw new UsernameNotFoundException("User not found");

    }
}
