package cinema;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}
