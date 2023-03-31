package cinema.repository;

import cinema.model.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class SeatRepository {

    private final List<Seat> allSeats;
    private Map<String, Seat> tokens;

    public SeatRepository() {
        allSeats = new ArrayList<>();
        tokens = new HashMap<>();
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                allSeats.add(new Seat(i, j));
            }
        }
    }

    public List<Seat> getAllSeats() {
        return allSeats;
    }

    public List<Seat> getAvailableSeats() {
        return allSeats.stream().filter(Seat::isAvailable).collect(Collectors.toList());
    }

    public ResponseEntity<Object> createTicket(Seat seat) {
        UUID token = UUID.randomUUID();
        tokens.put(String.valueOf(token), seat);
        var map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("ticket", seat);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> returnTicket(String token) {
        var map = new HashMap<String, Object>();
        if (tokens.containsKey(token)) {
            Seat seat = tokens.get(token);
            if (seat != null) {
                seat.setAvailable(true);
                map.put("returned_ticket", seat);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }
        map.put("error", "Wrong token!");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
