package pl.polsl.traini.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.model.registered.Registered;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainiUserDetailsService implements UserDetailsService {

    private final RegisterRepository registerRepository;

    @Autowired
    public TrainiUserDetailsService(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Registered registered = registerRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        return new User(registered.getEmail(), registered.getPassword(), new ArrayList<>());
    }
}
