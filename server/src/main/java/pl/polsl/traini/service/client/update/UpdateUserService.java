package pl.polsl.traini.service.client.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.model.dto.client.update.UpdateUserReq;
import pl.polsl.traini.model.user.User;

import java.util.Date;

@Service
public class UpdateUserService {
    private final UserRepository userRepository;

    @Autowired
    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUser(UpdateUserReq req, Long userId) {
        return userRepository.save(createUser(req, userId));
    }

    private User createUser(UpdateUserReq req, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Cant update user by id = " + id));

        user.setName("".equals(req.getName())? user.getName() : req.getName());
        user.setSurname("".equals(req.getSurname())? user.getSurname() : req.getSurname());
        user.setDescription("".equals(req.getDescription())? user.getDescription() : req.getDescription());
        user.setPhone("".equals(req.getPhone())? user.getPhone() : req.getPhone());
        user.setPhoto("".equals(req.getPhoto())? user.getPhoto() : req.getPhoto());
        user.setCity("".equals(req.getCity())? user.getCity() : req.getCity());
        user.setGender("".equals(req.getGender())? user.getGender() : req.getGender());
        user.setModifyAt(new Date());

        return userRepository.save(user);
    }
}
