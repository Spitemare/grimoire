package org.grimoire.security;

import static org.grimoire.repo.UserRepo.Specs.byUsername;
import org.grimoire.model.User;
import org.grimoire.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserRepoUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserRepoUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findOne(byUsername(username));
        if (user == null) throw new UsernameNotFoundException(username);
        return new GrimoireUserDetails(user);
    }

}
