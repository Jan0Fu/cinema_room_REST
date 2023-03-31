package cinema;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SeatRepository {

    private final List<Seat> allSeats;

    public SeatRepository() {
        allSeats = new ArrayList<>();
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
}
