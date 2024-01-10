package pl.polsl.traini.service.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.traini.database.LocationRepository;
import pl.polsl.traini.exceptions.NoTrainingException;
import pl.polsl.traini.model.location.Location;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location findByLocationId(Long locationId) {
        try {
            return locationRepository.findById(locationId)
                    .orElseThrow(() -> new NoTrainingException("No training for trainingId = " + locationId));
        } catch (NoTrainingException e) {
            System.out.println(e.getMessage());
            return new Location();
        }
    }

    public Location findByName(String name) {
        return locationRepository.findByName(name);
    }

    public List<Location> getListByIds(List<Long> ids) {
        return locationRepository.findAllById(ids);
    }

    public Location save(Location location){
        return locationRepository.save(location);
    }
}
