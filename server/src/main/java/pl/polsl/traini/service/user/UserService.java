package pl.polsl.traini.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.user.User;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserId(Long userId) {
        try {
            return userRepository.findById(userId)
                    .orElseThrow(() -> new NoTrainingException("No training for trainingId = " + userId));
        } catch (NoTrainingException e) {
            System.out.println(e.getMessage());
            return new User();
        }
    }

    public List<User> findByTrainerId(Long trainerId) {
        return userRepository.findByTrainerId(trainerId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
