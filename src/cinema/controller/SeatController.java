package cinema.controller;

import cinema.service.SeatService;
import cinema.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> reserveSeat(@RequestBody Seat seat) {
        return seatService.reserveSeat(seat);
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnTicket(@RequestBody Map<String, String> body) {
        return seatService.returnTicket(body.get("token"));
    }
}
