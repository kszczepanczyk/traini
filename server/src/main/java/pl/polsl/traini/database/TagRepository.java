package pl.polsl.traini.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.traini.model.tag.Tag;

@Repository
public interface TagRepository extends MongoRepository<Tag, Long> {
  Tag findTagByName(String name);
}
