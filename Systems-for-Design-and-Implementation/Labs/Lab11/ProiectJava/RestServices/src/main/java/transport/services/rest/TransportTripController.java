package transport.services.rest;

import transport.model.Trip;
import transport.model.TripDTO;
import transport.persistence.ITripRepository;
import transport.persistence.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/transport/trips")
public class TransportTripController {

    private static final String template = "Hello, %s!";

    @Autowired
    private ITripRepository tripRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    // FindAll Functionality
    @RequestMapping(method = RequestMethod.GET)
    public Trip[] getAll(){
        System.out.println("Get all trips ...");
        Iterable<Trip> iterable = tripRepository.findAll();
        List<Trip> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list.toArray(new Trip[0]);
    }

    // FindOne Functionality
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id "+id);
        Trip trip = tripRepository.findOne(Long.parseLong(id)).orElse(null);
        if (trip == null)
            return new ResponseEntity<String>("Trip not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Trip>(trip, HttpStatus.OK);
    }

    // Add Functionality
    @RequestMapping(method = RequestMethod.POST)
    public TripDTO create(@RequestBody TripDTO tripDTO){
        LocalDateTime dateDeparture = LocalDateTime.parse(tripDTO.getDateDeparture(), DateTimeFormatter.ISO_DATE_TIME);
        Trip trip = new Trip(tripDTO.getDestination(), dateDeparture, Long.parseLong(tripDTO.getNoOfAvailableSeats()));
        System.out.println("Creating trip ...");
        System.out.println(trip);
        tripRepository.save(trip);
        tripDTO.setId(String.valueOf(trip.getId()));
        System.out.println("Trip created ..." + tripDTO);
        return tripDTO;
    }

    // Update Functionality
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody TripDTO tripDTO){
        System.out.println("Updating trip with id: " + tripDTO.getId());
        if (!id.equals(tripDTO.getId())) {
            return new ResponseEntity<String>("Path Id and Object Id do not match!", HttpStatus.BAD_REQUEST);
        }
        try {
            Trip existingTrip = tripRepository.findOne(Long.parseLong(tripDTO.getId())).orElse(null);
            if (existingTrip == null)
                return new ResponseEntity<String>("Trip not found", HttpStatus.NOT_FOUND);
            else {
                existingTrip.setDestination(tripDTO.getDestination());
                existingTrip.setDateDeparture(LocalDateTime.parse(tripDTO.getDateDeparture(), DateTimeFormatter.ISO_DATE_TIME));
                existingTrip.setNoOfAvailableSeats(Long.parseLong(tripDTO.getNoOfAvailableSeats()));
                tripRepository.update(existingTrip);
                System.out.println("Trip updated ..." + tripDTO);
                return new ResponseEntity<TripDTO>(tripDTO, HttpStatus.OK);
            }
        } catch (RepositoryException ex){
            System.out.println("Ctrl Update trip exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // Delete Functionality
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting trip wit id: " + id);
        try {
            Trip deletedTrip = tripRepository.delete(Long.parseLong(id)).orElse(null);
            if (deletedTrip == null)
                return new ResponseEntity<String>("Trip not found", HttpStatus.NOT_FOUND);
            else {
                TripDTO tripDTO = new TripDTO();
                tripDTO.setId(String.valueOf(deletedTrip.getId()));
                tripDTO.setDestination(deletedTrip.getDestination());
                tripDTO.setDateDeparture(deletedTrip.getDateDeparture().format(DateTimeFormatter.ISO_DATE_TIME));
                tripDTO.setNoOfAvailableSeats(String.valueOf(deletedTrip.getNoOfAvailableSeats()));
                tripRepository.delete(Long.parseLong(id));
                System.out.println("Trip deleted ..." + tripDTO);
                return new ResponseEntity<TripDTO>(tripDTO, HttpStatus.OK);
            }
        } catch (RepositoryException ex){
            System.out.println("Ctrl Delete trip exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
