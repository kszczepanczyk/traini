package pl.polsl.traini.service.user.modify;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.*;
import pl.polsl.traini.exceptions.UserNotModifiedException;
import pl.polsl.traini.model.dto.user.UserModifyReq;
import pl.polsl.traini.model.location.Location;
import pl.polsl.traini.model.registered.Registered;
import pl.polsl.traini.model.tag.Tag;
import pl.polsl.traini.model.trainer.Trainer;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.seq.SeqGeneratorService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserModifyService {

    private final UserRepository userRepository;
    private final RegisterRepository registerRepository;
    private final TrainerRepository trainerRepository;
    private final TagRepository tagRepository;
    private final LocationRepository locationRepository;
    private final SeqGeneratorService generatorService;

    @Autowired
    public UserModifyService(UserRepository userRepository, RegisterRepository registerRepository, TrainerRepository trainerRepository, TagRepository tagRepository, LocationRepository locationRepository, SeqGeneratorService generatorService) {
        this.userRepository = userRepository;
      this.registerRepository = registerRepository;
      this.trainerRepository = trainerRepository;
      this.tagRepository = tagRepository;
      this.locationRepository = locationRepository;
      this.generatorService = generatorService;
    }

    public User modifyUser(UserModifyReq req, String username) throws UserNotModifiedException {

        return userRepository.findById(registerRepository.findByEmail(username).orElseThrow().getUserId())
                    .map(user -> userRepository.save(setNewValues(user, req)))
                    .orElseThrow( () -> new UserNotModifiedException("Cant modify user with userId = " + req.getId()));
    }

    private User setNewValues(User user, UserModifyReq req) {
        user.setName(req.getName());
        user.setSurname(req.getSurname());
        user.setPhone(req.getPhone());
        user.setCity(req.getCity());
        user.setDescription(req.getDescription());
        user.setModifyAt(new Date());
        user.setGender(req.getGender());

        Registered registered = registerRepository.findByUserId(user.getId()).orElseThrow();
        registered.setEmail(req.getEmail());
        registerRepository.save(registered);

        Trainer trainer = trainerRepository.findByRegisteredId(registered.getId()).orElseThrow();
        trainer.setTags(getTags(req.getTags()));
        trainer.setLocations(getLocations(req.getLocalization()));
        trainerRepository.save(trainer);

        return user;
    }

    private ArrayList<Long> getTags(List<String> tagNames) {
      ArrayList<Long> tagIds = new ArrayList<>();

        for (String name : tagNames) {
            Tag tag = tagRepository.findTagByName(name);
            if (tag  == null) {
              tag = tagRepository.save(Tag.builder()
                .id(generatorService.generateSeq(Tag.SEQUENCE_NAME))
                .name(name)
                .build());
            }

            tagIds.add(tag.getId());
        }

        return tagIds;
    }

  private ArrayList<Long> getLocations(List<String> locationNames) {
    ArrayList<Long> locationIds = new ArrayList<>();

    for (String name : locationNames) {
      Location location = locationRepository.findByName(name);
      if (location == null) {
        location = locationRepository.save(Location.builder()
          .id(generatorService.generateSeq(Location.SEQUENCE_NAME))
          .name(name)
          .build());
      }

      locationIds.add(location.getId());
    }

    return locationIds;
  }
}
