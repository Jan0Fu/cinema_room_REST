package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/seats")
    public Record availabilty() {
        return seatService.availableSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseSeat(@RequestBody Seat seat) {
        for (Seat s: seatService.getAllSeats()) {
            if (s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn()) {
                if (s.isAvailable()) {
                    s.setAvailable(false);
                    return new ResponseEntity<>(s, HttpStatus.OK);
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
}
