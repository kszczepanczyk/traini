package pl.polsl.traini.service.client.getInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.TrainingRepository;
import pl.polsl.traini.database.UserRepository;
import pl.polsl.traini.model.dto.client.info.GetClientInfoRsp;
import pl.polsl.traini.model.user.User;

@Service
public class GetClientInfoService {
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    @Autowired
    public GetClientInfoService(UserRepository userRepository, TrainingRepository trainingRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
    }

    public GetClientInfoRsp getClientInfo(Long clientId) {
        User user = userRepository.findById(clientId).orElseThrow(
                () -> new UsernameNotFoundException("Cant find user by userId = " + clientId));
        return createGetClientInfoRsp(user);
    }

    private GetClientInfoRsp createGetClientInfoRsp(User user) {
        return GetClientInfoRsp.builder()
                .id(user.getId())
                .description(user.getDescription())
                .name(user.getName())
                .surname(user.getSurname())
                .gender(user.getGender())
                .phone(user.getPhone())
                .photo(user.getPhoto())
                .city(user.getCity())
                .build();
    }
}
