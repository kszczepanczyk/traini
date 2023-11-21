package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.registered.Registered;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegisterRepository extends MongoRepository<Registered, Long> {
    Optional<Registered> findByEmail(String email);
}
