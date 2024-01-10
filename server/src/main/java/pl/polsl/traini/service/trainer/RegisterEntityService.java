package pl.polsl.traini.service.trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.RegisterRepository;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.registered.Registered;

@Service
public class RegisterEntityService {

    private final RegisterRepository registerRepository;

    @Autowired
    public RegisterEntityService(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    public Registered findByEmail(String email) {
        try {
            return registerRepository.findByEmail(email)
                    .orElseThrow(() -> new NoTrainingException("No register for email = " + email));
        } catch (NoTrainingException e) {
            System.out.println(e.getMessage());
            return new Registered();
        }
    }

    public Registered findByUserId(Long userId) {
        try {
            return registerRepository.findByUserId(userId)
                    .orElseThrow(() -> new NoTrainingException("No register for userId = " + userId));
        } catch (NoTrainingException e) {
            System.out.println(e.getMessage());
            return new Registered();
        }
    }

    public Registered save(Registered registered) {
        return registerRepository.save(registered);
    }
}
