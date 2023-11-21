package pl.polsl.traini.service.user.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.TagRepository;
import pl.polsl.traini.database.TrainerRepository;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.model.dto.user.UserInfoRsp;
import pl.polsl.traini.model.registered.Registered;
import pl.polsl.traini.model.tag.Tag;
import pl.polsl.traini.model.trainer.Trainer;
import pl.polsl.traini.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserInfoService {
    private final RegisterRepository registerRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final TagRepository tagRepository;

    @Autowired
    public UserInfoService(RegisterRepository registerRepository, UserRepository userRepository, TrainerRepository trainerRepository, TagRepository tagRepository) {
        this.registerRepository = registerRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.tagRepository = tagRepository;
    }

    public UserInfoRsp getUserInfo(String username) {
        Registered registered = registerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No register with email = " + username));

        User user = userRepository.findById(registered.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("No user with email = " + username));

        Trainer trainer = trainerRepository.findByRegisteredId(registered.getId())
                .orElseThrow(() -> new UsernameNotFoundException("No trainer with email = " + username));

        return createUserInfoRsp(registered, user, trainer);
    }

    private UserInfoRsp createUserInfoRsp(Registered registered, User user, Trainer trainer) {
        return UserInfoRsp.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .photoB64(user.getPhoto())
                .tags(getTags(trainer.getTags()))
                .description(user.getDescription())
                .phone(user.getPhone())
                .email(registered.getEmail())
                .city(user.getCity())
                .gender(user.getGender())
                .trainer("T".equals(registered.getType()))
                .build();
    }

    private List<String> getTags(List<Long> tagsIds) {
        List<Tag> tags = tagRepository.findAllById(tagsIds);

        List<String> tagsNames = new ArrayList<>();

        for(Tag tag : tags) {
            tagsNames.add(tag.getName());
        }

        return tagsNames;
    }
}
