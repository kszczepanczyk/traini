package pl.polsl.traini.model.token;


import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "Token")
public class Token {

  @Transient
  public static final String SEQUENCE_NAME = "token_seq";

  public long id;

  public String token;

  public TokenType tokenType = TokenType.BEARER;

  public boolean revoked;

  public boolean expired;

  public String username;

}
