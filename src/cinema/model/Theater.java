package cinema.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Theater {

    private final List<Seat> allSeats;
    private Map<String, Seat> tokens;
    private int currentIncome;

    public Theater() {
        allSeats = new ArrayList<>();
        tokens = new HashMap<>();
        currentIncome = 0;
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
        currentIncome += seat.getPrice();
        var map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("ticket", seat);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> returnTicket(Map<String, String> body) {
        Seat seat = tokens.get(body.get("token"));
        if (seat != null) {
            seat.setAvailable(true);
            currentIncome -= seat.getPrice();
            return new ResponseEntity<>(Map.of("returned_ticket", seat), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    public int getCurrentIncome() {
        return currentIncome;
    }
}
