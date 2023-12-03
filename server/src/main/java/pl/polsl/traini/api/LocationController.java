package pl.polsl.traini.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.traini.config.JwtService;
import pl.polsl.traini.model.dto.list.AddClientReq;
import pl.polsl.traini.model.location.Location;
import pl.polsl.traini.model.user.User;
import pl.polsl.traini.service.list.add.AddClientService;
import pl.polsl.traini.service.list.getList.GetClientListService;
import pl.polsl.traini.service.location.get.GetLocationsService;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

  private final JwtService jwtService;
  private final GetLocationsService getLocationsService;

  @GetMapping
  public ResponseEntity<List<Location>> getLocations(@RequestHeader(name="Authorization") String token) {
    String username = jwtService.getUsername(token);
    log.info("Get locations for trainer = {}", username);
    return ResponseEntity.ok(getLocationsService.getLocations(username));
  }
}
