package pl.polsl.traini.database;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.polsl.traini.model.token.Token;

import java.util.List;
import java.util.Optional;

@Document(collection = "Token")
public interface TokenRepository extends MongoRepository<Token, Integer> {

  Optional<Token> findByToken(String token);

  List<Token> findByUsernameOrderById(String username);
}
