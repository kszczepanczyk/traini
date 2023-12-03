package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.location.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends MongoRepository<Location, Long> {
    Location findByName(String name);
}
