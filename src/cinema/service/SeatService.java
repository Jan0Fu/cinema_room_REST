package cinema.service;

import cinema.model.Seat;
import cinema.model.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeatService {

    private final Theater theater;

    public record SeatsDTO(int totalRows, int totalColumns, List<Seat> availableSeats) {}

    @Autowired
    public SeatService(Theater theater) {
        this.theater = theater;
    }

    public SeatsDTO availableSeats() {
        return new SeatsDTO(9, 9, theater.getAvailableSeats());
    }

    public List<Seat> getAllSeats() {
        return theater.getAllSeats();
    }

    public ResponseEntity<Object> reserveSeat(Seat seat) {
        for (Seat s: getAllSeats()) {
            if (s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn()) {
                if (s.isAvailable()) {
                    s.setAvailable(false);
                    return theater.createTicket(s);
                } else {
                    return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> returnTicket(Map<String, String> body) {
        if (!body.containsKey("token")) {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
        return theater.returnTicket(body);
    }

    public ResponseEntity<Object> getStats(String password) {
        if (password.equals("super_secret")) {
            var map = new LinkedHashMap<String, Integer>();
            var seats = theater.getAvailableSeats().size();
            map.put("current_income", theater.getCurrentIncome());
            map.put("number_of_available_seats", seats);
            map.put("number_of_purchased_tickets", (81 - seats));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }
}
