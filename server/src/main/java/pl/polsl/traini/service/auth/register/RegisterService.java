package pl.polsl.traini.service.auth.register;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.database.TrainerRepository;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.model.dto.auth.register.UserRegisterReq;
import pl.polsl.traini.model.registered.Registered;
import pl.polsl.traini.model.trainer.Trainer;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.seq.SeqGeneratorService;

import java.util.ArrayList;
import java.util.Date;

@Service
@Slf4j
public class RegisterService {

    private final SeqGeneratorService generatorService;
    private final PasswordEncoder passwordEncoder;
    private final RegisterRepository registeredRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;



    @Autowired
    public RegisterService(SeqGeneratorService generatorService, PasswordEncoder passwordEncoder, RegisterRepository registeredRepository, UserRepository userRepository, TrainerRepository trainerRepository) {
        this.generatorService = generatorService;
        this.passwordEncoder = passwordEncoder;
        this.registeredRepository = registeredRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
    }

    public String register(UserRegisterReq req) {
        //potem sprawdzenie czy registered istnieje
        log.info("trying to register user = {}", req);

        User user = userRepository.save(createUserEntity(req));

        if(user.getId() <= 0) {
            return"Can't add user!";
        }

        Registered registered = registeredRepository.save(createRegisteredEntity(req, user.getId()));

        if(registered.getId() <= 0) {
            log.warn("Can't add registered!");
            return "Can't add registered!";
        }

        Trainer trainer = trainerRepository.save(createTrainerEntity(registered.getId()));

        return "ADDED USER " + user.getName() + " " + user.getSurname();
    }


    private User createUserEntity(UserRegisterReq req) {
        return new User(
                generatorService.generateSeq(User.SEQUENCE_NAME),
                req.getName(),
                req.getSurname(),
                req.getPhone(),
                "",
                req.getCity(),
                req.getGender(),
                "",
                new Date(),
                new Date()
        );
    }

    private Registered createRegisteredEntity(UserRegisterReq req, long userId) {
        return new Registered(
                generatorService.generateSeq(Registered.SEQUENCE_NAME),
                userId,
                req.getEmail(),
                passwordEncoder.encode(req.getPassword()),
                "T",
                true
        );
    }

    private Trainer createTrainerEntity(long registeredId) {
      return new Trainer(
        generatorService.generateSeq(Trainer.SEQUENCE_NAME),
        registeredId,
        new ArrayList<>(),
        new ArrayList<>()
      );
    }
}
