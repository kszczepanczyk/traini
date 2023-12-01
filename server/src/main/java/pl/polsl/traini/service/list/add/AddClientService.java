package pl.polsl.traini.service.list.add;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.TrainerRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.model.dto.list.AddClientReq;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.seq.SeqGeneratorService;

import java.util.Date;

@Service
public class AddClientService {

    private final UserRepository userRepository;
    private final SeqGeneratorService generatorService;
    private final RegisterRepository registerRepository;
    private final TrainerRepository trainerRepository;

    @Autowired
    public AddClientService(UserRepository userRepository, SeqGeneratorService generatorService, RegisterRepository registerRepository, TrainerRepository trainerRepository) {
        this.userRepository = userRepository;
        this.generatorService = generatorService;
      this.registerRepository = registerRepository;
      this.trainerRepository = trainerRepository;
    }


    public User addUser(AddClientReq req, String username) {
      long trainerId = trainerRepository.findByRegisteredId(
        registerRepository.findByEmail(username).orElseThrow().getId()
      ).orElseThrow().getId();
        return userRepository.save(createUser(req, trainerId));
    }

    private User createUser(AddClientReq req, long trainerId) {
        return User.builder()
                .id(generatorService.generateSeq(User.SEQUENCE_NAME))
                .trainerId(trainerId)
                .name(req.getName())
                .surname(req.getSurname())
                .city(req.getCity())
                .description(req.getDescription())
                .gender(req.getGender())
                .phone(req.getPhone())
                .createdAt(new Date())
                .modifyAt(new Date())
                .build();
    }
}
