package pl.polsl.traini.service.home.avatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.model.dto.home.avatar.HomeGetAvatarRsp;
import pl.polsl.traini.model.user.User;

@Service
public class HomeAvatarService {
    private final UserRepository userRepository;
    private final RegisterRepository registerRepository;

    @Autowired
    public HomeAvatarService(UserRepository userRepository, RegisterRepository registerRepository) {
        this.userRepository = userRepository;
        this.registerRepository = registerRepository;
    }

    public HomeGetAvatarRsp getAvatar (String username) {
        User user = userRepository.findById(
                    registerRepository.findByEmail(username).orElseThrow(
                        () -> new UsernameNotFoundException("Cant find registred by email = " + username)
                    ).getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("Cant find user by email = " + username));
        return HomeGetAvatarRsp.builder()
          .name(user.getName())
          .photo(user.getPhoto())
          .build();
    }
}
