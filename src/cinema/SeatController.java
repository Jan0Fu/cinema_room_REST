package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeatController {

    private SeatRepository seatRepository;

    public record SeatsDTO(int totalRows, int totalColumns, List<Seat> availableSeats) {}

    @Autowired
    public SeatController(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @GetMapping("/seats")
    public SeatsDTO availabilty() {
        return new SeatsDTO(9, 9, seatRepository.getSeats());
    }
}
