package pl.polsl.traini.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.model.dto.user.UserInfoRsp;
import pl.polsl.traini.model.dto.user.UserModifyReq;
import pl.polsl.traini.model.location.Location;
import pl.polsl.traini.model.registered.Registered;
import pl.polsl.traini.model.tag.Tag;
import pl.polsl.traini.model.trainer.Trainer;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.location.LocationService;
import pl.polsl.traini.service.seq.SeqGeneratorService;
import pl.polsl.traini.service.tag.TagService;
import pl.polsl.traini.service.trainer.RegisterEntityService;
import pl.polsl.traini.service.trainer.TrainerService;
import pl.polsl.traini.service.user.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TrainerApiImpl {
    private final UserService userService;
    private final RegisterEntityService registerEntityService;
    private final TrainerService trainerService;
    private final TagService tagService;
    private final LocationService locationService;
    private final SeqGeneratorService generatorService;

    @Autowired
    public TrainerApiImpl(UserService userService, RegisterEntityService registerEntityService, TrainerService trainerService, TagService tagService, LocationService locationService, SeqGeneratorService generatorService) {
        this.userService = userService;
        this.registerEntityService = registerEntityService;
        this.trainerService = trainerService;
        this.tagService = tagService;
        this.locationService = locationService;
        this.generatorService = generatorService;
    }

    public User modify(UserModifyReq req, String username) {
        return setNewValues(userService.findByUserId(registerEntityService.findByEmail(username).getUserId()), req);
    }

    public UserInfoRsp getTrainerInfo(String username) {
        Registered registered = registerEntityService.findByEmail(username);
        User user = userService.findByUserId(registered.getUserId());
        Trainer trainer = trainerService.findByRegisterId(registered.getId());

        return createUserInfoRsp(registered, user, trainer);
    }

    public List<Location> getLocations(String username){
        Registered registered = registerEntityService.findByEmail(username);
        Trainer trainer = trainerService.findByRegisterId(registered.getId());

        return locationService.getListByIds(trainer.getLocations());
    }

    private User setNewValues(User user, UserModifyReq req) {
        user.setName(req.getName());
        user.setSurname(req.getSurname());
        user.setPhone(req.getPhone());
        user.setCity(req.getCity());
        user.setDescription(req.getDescription());
        user.setModifyAt(new Date());
        user.setGender(req.getGender());
        userService.save(user);

        Registered registered = registerEntityService.findByUserId(user.getId());
        registered.setEmail(req.getEmail());
        registerEntityService.save(registered);

        Trainer trainer = trainerService.findByRegisterId(registered.getId());
        trainer.setTags(getTags(req.getTags()));
        trainer.setLocations(getLocations(req.getLocations()));
        trainerService.save(trainer);

        return user;
    }

    private ArrayList<Long> getTags(List<String> tagNames) {
        ArrayList<Long> tagIds = new ArrayList<>();

        for (String name : tagNames) {
            Tag tag = tagService.getTagByName(name);
            if (tag  == null) {
                tag = tagService.save(Tag.builder()
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
            Location location = locationService.findByName(name);
            if (location == null) {
                location = locationService.save(Location.builder()
                        .id(generatorService.generateSeq(Location.SEQUENCE_NAME))
                        .name(name)
                        .build());
            }

            locationIds.add(location.getId());
        }

        return locationIds;
    }

    private List<String> getTagsForGet(List<Long> tagsIds) {
        List<Tag> tags = tagService.getListByIds(tagsIds);

        List<String> tagsNames = new ArrayList<>();

        for(Tag tag : tags) {
            tagsNames.add(tag.getName());
        }

        return tagsNames;
    }
    private List<String> getLocationsForGet(List<Long> locationsIds) {
        List<Location> locations = locationService.getListByIds(locationsIds);

        List<String> locationNames = new ArrayList<>();

        for(Location location : locations) {
            locationNames.add(location.getName());
        }

        return locationNames;
    }

    private UserInfoRsp createUserInfoRsp(Registered registered, User user, Trainer trainer) {
        return UserInfoRsp.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .photoB64(user.getPhoto())
                .tags(getTagsForGet(trainer.getTags()))
                .locations(getLocationsForGet(trainer.getLocations()))
                .description(user.getDescription())
                .phone(user.getPhone())
                .email(registered.getEmail())
                .city(user.getCity())
                .gender(user.getGender())
                .trainer("T".equals(registered.getType()))
                .id(user.getId())
                .build();
    }
}
