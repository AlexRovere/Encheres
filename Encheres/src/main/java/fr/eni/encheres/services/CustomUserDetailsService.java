package fr.eni.encheres.services;

import fr.eni.encheres.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService {

    public void updateCredit(int newCredit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (userDetails instanceof CustomUserDetails) {
            CustomUserDetails customer = (CustomUserDetails) userDetails;

            customer.setCredit(newCredit);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
