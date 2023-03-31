package cinema.service;

import cinema.model.Seat;
import cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public record SeatsDTO(int totalRows, int totalColumns, List<Seat> availableSeats) {}

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public SeatsDTO availableSeats() {
        return new SeatsDTO(9, 9, seatRepository.getAvailableSeats());
    }

    public List<Seat> getAllSeats() {
        return seatRepository.getAllSeats();
    }

    public ResponseEntity<Object> reserveSeat(Seat seat) {
        for (Seat s: getAllSeats()) {
            if (s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn()) {
                if (s.isAvailable()) {
                    s.setAvailable(false);
                    return seatRepository.createTicket(s);
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("error", "The ticket has been already purchased!");
                    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
                }
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("error","The number of a row or a column is out of bounds!");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> returnTicket(String token) {
        return seatRepository.returnTicket(token);
    }
}
