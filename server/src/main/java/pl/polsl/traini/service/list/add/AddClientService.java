package pl.polsl.traini.service.list.add;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.model.dto.list.AddClientReq;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.seq.SeqGeneratorService;

import java.util.Date;

@Service
public class AddClientService {

    private final UserRepository userRepository;
    private final SeqGeneratorService generatorService;

    @Autowired
    public AddClientService(UserRepository userRepository, SeqGeneratorService generatorService) {
        this.userRepository = userRepository;
        this.generatorService = generatorService;
    }


    public User addUser(AddClientReq req) {
        return userRepository.save(createUser(req));
    }

    private User createUser(AddClientReq req) {
        return User.builder()
                .id(generatorService.generateSeq(User.SEQUENCE_NAME))
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
