package pl.polsl.traini.service.user.modify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.exceptions.UserNotModifiedException;
import pl.polsl.traini.model.dto.user.UserModifyReq;
import pl.polsl.traini.model.user.User;

import java.util.Date;

@Service
public class UserModifyService {

    private final UserRepository userRepository;

    @Autowired
    public UserModifyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User modifyUser(UserModifyReq req) throws UserNotModifiedException {
        return userRepository.findById(req.getId())
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

        return user;
    }
}
