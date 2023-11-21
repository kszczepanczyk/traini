package pl.polsl.traini.service.home.avatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.UserRepository;

@Service
public class HomeAvatarService {
    private final UserRepository userRepository;
    private final RegisterRepository registerRepository;

    @Autowired
    public HomeAvatarService(UserRepository userRepository, RegisterRepository registerRepository) {
        this.userRepository = userRepository;
        this.registerRepository = registerRepository;
    }

    public String getAvatar (String username) {
        return userRepository.findById(
                    registerRepository.findByEmail(username).orElseThrow(
                        () -> new UsernameNotFoundException("Cant find registred by email = " + username)
                    ).getId())
                .orElseThrow(() -> new UsernameNotFoundException("Cant find user by email = " + username))
            .getPhoto();
    }
}
