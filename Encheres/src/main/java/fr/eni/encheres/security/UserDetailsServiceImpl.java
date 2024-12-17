package fr.eni.encheres.security;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;
    public UserDetailsServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Utilisateur> userOptional = utilisateurRepository.getByLogin(username);
//        if(userOptional.isPresent()) {
//            Utilisateur user = userOptional.get();
//            List<GrantedAuthority> roles = Arrays.stream(user.getRoles().split(","))
//                    .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
//                    .collect(Collectors.toList());
//            return User.builder().username(user.getEmail()).password(user.getMotDePasse()).authorities(roles).build();
//        }

        throw new UsernameNotFoundException("User not found");

    }
}
