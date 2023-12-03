package pl.polsl.traini.service.location.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.*;
import pl.polsl.traini.model.location.Location;
import pl.polsl.traini.model.registered.Registered;
import pl.polsl.traini.model.trainer.Trainer;

import java.util.List;

@Service
public class GetLocationsService {
  private final RegisterRepository registerRepository;
  private final TrainerRepository trainerRepository;
  private final LocationRepository locationRepository;
  @Autowired
  public GetLocationsService(RegisterRepository registerRepository, TrainerRepository trainerRepository, LocationRepository locationRepository) {
    this.registerRepository = registerRepository;
    this.trainerRepository = trainerRepository;
    this.locationRepository = locationRepository;
  }

  public List<Location> getLocations(String username){
    Registered registered = registerRepository.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("No register with email = " + username));
    Trainer trainer = trainerRepository.findByRegisteredId(registered.getId())
      .orElseThrow(() -> new UsernameNotFoundException("No trainer with email = " + username));

    return locationRepository.findAllById(trainer.getLocations());
  }
}
