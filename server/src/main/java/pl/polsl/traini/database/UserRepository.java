package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.user.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
}
